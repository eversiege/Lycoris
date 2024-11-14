package org.momento.lycoris.mixins.mixin.classe;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;

public class ClassWrapper implements ByteCodec {


    private final int magic;
    private final char minorVersion;
    private final char majorVersion;
    private final ConstantPool[] constantPool;

    public ClassWrapper() {}


    @Override
    public void encode(ByteBuffer buffer) {

    }
}