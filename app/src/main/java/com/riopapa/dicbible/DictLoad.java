package com.riopapa.dicbible;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DictLoad {

    public List<String> get(File dicFolder) {

        File [] dicList = dicFolder.listFiles((dir, name) ->
                (name.endsWith("txt")));
        List<String> dicts = new ArrayList<>();
        for (File file : dicList) {
            String d = file.getName();
            dicts.add(d.substring(0, d.length() - 4));
        }
        Collections.sort(dicts);
        return dicts;
    }

}