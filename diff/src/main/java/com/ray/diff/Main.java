package com.ray.diff;

import com.ray.diff.diff.DexDiff;
import com.ray.diff.diff.DexPatch;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Main {


    public static void main(String[] args) {
        //生成差分包
        try {
            DexDiff dexDiff = new DexDiff(new File("old.dex"),
                    new File("new.dex"));
            dexDiff.diff(new File("patch.dex"));

            //合成apk
            DexPatch dexPatch = new DexPatch(new File("old.dex"),
                    new File("patch.dex"));
            dexPatch.patch(new File("new2.dex"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
