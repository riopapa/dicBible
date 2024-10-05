package beautiful.life.dicbible;

import static beautiful.life.dicbible.Vars.keyText;
import static beautiful.life.dicbible.Vars.searchDepth;
import static beautiful.life.dicbible.Vars.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class DictTable {

    List<Vars.KeyRef> read(File packageFolder) {
        final String EUC_KR = "UTF-8";
        final int BUFFER_SIZE = 81920;
        final File jsonFile = new File(packageFolder, "dict/keyRef.json");

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(jsonFile), EUC_KR), BUFFER_SIZE);
        } catch (Exception e) {
            utils.showSnackBar("파일 없음", jsonFile + " 을 찾지 못 해요");
            e.printStackTrace();
        }

        String json = "";
        try {
            json = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {   // no json file
            utils.showSnackBar("파일 없음", jsonFile + " 파일 내용이 없어요");
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Vars.KeyRef>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    List<Vars.bcv> where(String key) {
        for (int i = 0; i < Vars.keyRefs.size(); i++) {
            int comp = key.compareTo(Vars.keyRefs.get(i).key);
            if (comp == 0) {
                return Vars.keyRefs.get(i).bcvs;
            } else if (comp < 0)
                break;
        }
        return null;
    }

}