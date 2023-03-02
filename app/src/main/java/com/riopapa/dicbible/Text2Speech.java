package com.riopapa.dicbible;

import static com.riopapa.dicbible.Vars.bibleMake;
import static com.riopapa.dicbible.Vars.biblePitch;
import static com.riopapa.dicbible.Vars.bibleSpeed;
import static com.riopapa.dicbible.Vars.bibleTTS;
import static com.riopapa.dicbible.Vars.bibleTexts;
import static com.riopapa.dicbible.Vars.fullBibleNames;
import static com.riopapa.dicbible.Vars.hymnAccompany;
import static com.riopapa.dicbible.Vars.hymnMake;
import static com.riopapa.dicbible.Vars.hymnSpeed;
import static com.riopapa.dicbible.Vars.isReadingNow;
import static com.riopapa.dicbible.Vars.mContext;
import static com.riopapa.dicbible.Vars.menuColorBack;
import static com.riopapa.dicbible.Vars.nowBible;
import static com.riopapa.dicbible.Vars.nowChapter;
import static com.riopapa.dicbible.Vars.nowHymn;
import static com.riopapa.dicbible.Vars.packageFolder;
import static com.riopapa.dicbible.Vars.utils;
import static com.riopapa.dicbible.Vars.vCenterAction;
import static com.riopapa.dicbible.Vars.verMax;

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
                Toast.makeText(mContext, file.getName()+" 성경 낭독 파일이 없습니다", Toast.LENGTH_LONG).show();
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
        String match = "[^\uAC00-\uD7A3xfe./,\\s]"; // 한글만 OK
        text = text.replaceAll(match, "");
        text = (v + 1) + "절.. " + text;
        if (para != null)
            text = para + ". . "+ text;
        if (v == 0) {
            text = fullBibleNames[nowBible] + ". " + nowChapter + " " + ((nowBible == 19) ? "편" : "장") + " 말씀입니다.." + text;
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
            utils.showSnackBar("찬송가 "+nowHymn+"장 " + ((hymnAccompany)? "반주":"찬양"), " 파일이 없습니다");
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