package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.values;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;

import java.nio.ByteBuffer;

public abstract class ElementValue implements SizedByteCodec {

    public enum Tag {
        BYTE((byte) 'B'),
        CHAR((byte) 'C'),
        DOUBLE((byte) 'D'),
        FLOAT((byte) 'F'),
        INT((byte) 'I'),
        LONG((byte) 'J'),
        SHORT((byte) 'S'),
        BOOLEAN((byte) 'Z'),
        STRING((byte) 's'),
        ENUMCONSTANT((byte) 'e'),
        CLASS((byte) 'c'),
        ANNOTATION((byte) '@'),
        ARRAY((byte) '[');

        private final byte value;

        Tag(final byte value) {
            this.value = value;
        }

        public byte getValue() { return value; }

        public static Tag fromValue(final byte value) {
            for (final Tag tag : Tag.values()) {
                if (tag.getValue() == value)
                    return tag;
            }
            return null;
        }
    }

    private final Tag tag;

    public ElementValue(final Tag tag) {
        this.tag = tag;
    }

    public Tag getTag() { return tag; }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.put(tag.getValue());
    }
}
