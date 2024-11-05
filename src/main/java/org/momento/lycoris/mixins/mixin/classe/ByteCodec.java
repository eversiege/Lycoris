package org.momento.lycoris.mixins.mixin.classe;

import java.nio.ByteBuffer;

public interface ByteCodec<T> {

    void encode(ByteBuffer buffer);

}
