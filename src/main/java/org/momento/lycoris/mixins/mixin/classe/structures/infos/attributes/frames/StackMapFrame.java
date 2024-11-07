package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;

import java.nio.ByteBuffer;

public abstract class StackMapFrame implements SizedByteCodec {

    public static enum Type {
        SAME((byte) 0),
        SAME_LOCAL_1_STACk_ITEM((byte) 64),
        SAME_LOCAL_1_STACK_ITEM_EXTENDED((byte) 247),
        CHOP((byte) 248),
        SAME_FRAME_EXTENDED((byte) 251),
        APPEND((byte) 252),
        FULL_FRAME((byte) 255);

        private byte value;

        Type(final byte value) {
            this.value = value;
        }

        public byte getValue() { return value; }

        public static Type fromValue(final byte value) {
            Type type;
            if (value < 64)
                type = SAME;
            else if (value < 128)
                type = SAME_LOCAL_1_STACk_ITEM;
            else if (value < 248)
                type = SAME_LOCAL_1_STACK_ITEM_EXTENDED;
            else if (value < 251)
                type = CHOP;
            else if (value == 251)
                type = SAME_FRAME_EXTENDED;
            else if (value < 255)
                type = APPEND;
            else
                type = FULL_FRAME;
            type.value = value;
            return type;
        }
    }

    private final Type type;

    public StackMapFrame(final Type type) {
        this.type = type;
    }

    public Type getType() { return type; }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.put(type.value);
    }
}
