package org.momento.lycoris.mixins.mixin.classe.structures.infos;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;

public class UTF8Info extends Info {

    private final char length;
    private final byte bytes[];

    public UTF8Info(final ConstantPool.Tag tag, final char length, final byte[] bytes) {
        super(tag);
        this.length = length;
        this.bytes = bytes;
    }

    public char getLength() { return length; }
    public byte[] getBytes() { return bytes; }

    public static UTF8Info decode(ByteBuffer buffer) {
        char length = buffer.getChar();
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        return new UTF8Info(ConstantPool.Tag.UTF8, length, bytes);
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar(length);
        buffer.put(bytes);
    }
}
