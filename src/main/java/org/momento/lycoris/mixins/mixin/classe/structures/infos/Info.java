package org.momento.lycoris.mixins.mixin.classe.structures.infos;

import org.momento.lycoris.mixins.mixin.classe.ByteCodec;
import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;

public abstract class Info implements ByteCodec<Info> {

    private ConstantPool.Tag tag;

    public Info(final ConstantPool.Tag tag) {
        this.tag = tag;
    }

    public ConstantPool.Tag getTag() { return tag; }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.put(tag.getValue());
    }
}
