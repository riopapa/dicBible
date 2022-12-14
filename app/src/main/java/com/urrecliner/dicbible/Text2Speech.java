package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.biblePitch;
import static com.urrecliner.dicbible.Vars.bibleSpeed;
import static com.urrecliner.dicbible.Vars.bibleTTS;
import static com.urrecliner.dicbible.Vars.bibleTexts;
import static com.urrecliner.dicbible.Vars.fullBibleNames;
import static com.urrecliner.dicbible.Vars.hymnAccompany;
import static com.urrecliner.dicbible.Vars.hymnSpeed;
import static com.urrecliner.dicbible.Vars.isReadingNow;
import static com.urrecliner.dicbible.Vars.mContext;
import static com.urrecliner.dicbible.Vars.bibleMake;
import static com.urrecliner.dicbible.Vars.hymnMake;
import static com.urrecliner.dicbible.Vars.verMax;
import static com.urrecliner.dicbible.Vars.menuColorBack;
import static com.urrecliner.dicbible.Vars.nowBible;
import static com.urrecliner.dicbible.Vars.nowChapter;
import static com.urrecliner.dicbible.Vars.nowHymn;
import static com.urrecliner.dicbible.Vars.packageFolder;
import static com.urrecliner.dicbible.Vars.utils;
import static com.urrecliner.dicbible.Vars.vCenterAction;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.util.Timer;
import java.util.TimerTask;

class Text2Speech {

    private TextToSpeech mTTS = null;
    private int ttsVerseNow = 0;

    void setReady(Context context) {
        mTTS = new TextToSpeech(context, ttsInitListener);
    }

    private final TextToSpeech.OnInitListener ttsInitListener = status -> {
        if (status != TextToSpeech.SUCCESS)
            return;
        mTTS.setPitch((float) biblePitch / 100);
        mTTS.setSpeechRate((float) bibleSpeed / 100);
        mTTS.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onDone(String utteranceId) {
                ttsVerseNow++;
                if (isReadingNow && ttsVerseNow < verMax)
                    readBibleTTS(ttsVerseNow);
                else if (isReadingNow) {
                    bibleMake.goBibleRight();
                    new Timer().schedule(new TimerTask() {
                        public void run() {
                            ttsVerseNow = 0;
                            readBibleTTS(ttsVerseNow);
                        }
                    }, 2000);
                }
            }

            @Override
            public void onError(String utteranceId) {
            }

            @Override
            public void onStart(String utteranceId) {
            }
        });
    };

    private MediaPlayer mediaPlayer = null;

    void playBible() {

        mediaPlayer = new MediaPlayer();
        if (bibleTTS) {
            readBibleTTS(0);
        } else {
            String fileName = packageFolder.getAbsolutePath()+"/bible_mp3/"
                    +nowBible+"_"+nowChapter+".mp3z";
            File file = new File(fileName);
            FileDescriptor fd;
            if (!file.exists()) {
                Toast.makeText(mContext, file.getName()+" ?????? ?????? ????????? ????????????", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                FileInputStream fs = new FileInputStream(file);
                fd = fs.getFD();
                mediaPlayer.setDataSource(fd);
                fs.close();
                mediaPlayer.setLooping(false);
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed((float)bibleSpeed/100));
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setPitch((float)biblePitch/100));
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mediaPlayer.setOnCompletionListener(mp -> {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                if (isReadingNow) {
                    bibleMake.goBibleRight();
                    new Timer().schedule(new TimerTask() {
                        public void run() {
                            playBible();
                        }
                    }, 2000);
                }
            });
            mediaPlayer.start();
        }
    }

    private void readBibleTTS(int v) {
        String text;
        String para = null;
        ttsVerseNow = v;
        text = bibleTexts[v].substring(0, bibleTexts[v].indexOf("`a"));
        if (text.charAt(0) == '{') {
            para = text.substring(1,(text.indexOf("}")));
            text = text.substring(text.indexOf("}")+1);
        }
        String match = "[^\uAC00-\uD7A3xfe./,\\s]"; // ????????? OK
        text = text.replaceAll(match, "");
        text = (v + 1) + "???.. " + text;
        if (para != null)
            text = para + ". . "+ text;
        if (v == 0) {
            text = fullBibleNames[nowBible] + ". " + nowChapter + " " + ((nowBible == 19) ? "???" : "???") + " ???????????????.." + text;
        }
        try {
//            HashMap<String, String> map = new HashMap<>();
//            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
//            mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, map);
            Bundle params = new Bundle();
            params.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 1f); // change the 0.5f to any value from 0f-1f (1f is default)
            mTTS.speak(text, TextToSpeech.QUEUE_ADD, params, TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);

        } catch (Exception e) {
            String logId = "tts";
            utils.log(logId, "ttsSpeak\n" + e);
        }
    }

    void playHymn() {
        mediaPlayer = new MediaPlayer();
        String fileName = packageFolder.getAbsolutePath()+((hymnAccompany)? "/hymn_mp3/":"/hymn_play/")+nowHymn+".mp3z";
        File file = new File(fileName);
        FileDescriptor fd;
        if (file.exists()) {
            try {
                FileInputStream fs = new FileInputStream(file);
                fd = fs.getFD();
                mediaPlayer.setDataSource(fd);
                fs.close();
                mediaPlayer.setLooping(false);
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams()
                        .setSpeed((float) hymnSpeed / 100));
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mediaPlayer.setOnCompletionListener(mp -> {
                mediaPlayer.stop();
                mediaPlayer.release();
                if (isReadingNow) {
                    hymnMake.goHymnRight();
                    new Timer().schedule(new TimerTask() {
                        public void run() {
                            playHymn();
                        }
                    }, 2000);
                }
            });
            mediaPlayer.start();
            isReadingNow = true;
        } else {
            utils.showSnackBar("????????? "+nowHymn+"??? " + ((hymnAccompany)? "??????":"??????"), " ????????? ????????????");
            isReadingNow = false;
        }
    }

    void stopPlay() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mTTS.stop();
        isReadingNow = false;
        vCenterAction.setBackgroundColor(menuColorBack);
    }
}