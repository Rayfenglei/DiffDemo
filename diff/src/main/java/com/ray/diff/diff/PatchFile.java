package com.ray.diff.diff;

import com.ray.diff.PatchOperation;
import com.ray.diff.android.StringIdItem;
import com.ray.diff.util.BufferUtil;
import com.ray.diff.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class PatchFile {

    public List<PatchOperation> patchOperations = new ArrayList<>();

    public PatchFile(File file) throws IOException {
        byte[] bytes = FileUtil.readFile(file);
        ByteBuffer in = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
        while (in.position() < in.limit()) {
            int op = in.getInt();
            int index = in.getInt();
            int size = BufferUtil.readUnsignedLeb128(in);
            String data = BufferUtil.readMutf8(in, size);
            PatchOperation patchOperation = new PatchOperation(op, index, new StringIdItem(size, data));
            patchOperations.add(patchOperation);
        }


    }
}
