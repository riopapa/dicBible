package com.urrecliner.dicbible;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DictLoad {
    final File dicFolder;
    public DictLoad(File dicFolder) {
        this.dicFolder = dicFolder;
    }

    public List<String> get() {

        File [] dicList = dicFolder.listFiles((dir, name) ->
                (name.endsWith("txt")));
        List<String> dicts = new ArrayList<>();
        for (int i = 0; i < dicList.length; i++) {
            String d = dicList[i].getName();
            dicts.add(d.substring(0, d.length()-4));
        }
        Collections.sort(dicts);
        return dicts;
    }

}