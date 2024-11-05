package org.momento.lycoris.mixins.mixin.classe.structures;

import org.momento.lycoris.mixins.mixin.classe.ByteCodec;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.*;

import java.nio.ByteBuffer;

public class ConstantPool implements ByteCodec<ConstantPool> {

    public enum Tag {

        Class((byte) 7),
        FieldRef((byte) 9),
        MethodRef((byte) 10),
        InterfaceMethodRef((byte) 1),
        String((byte) 8),
        Integer((byte) 3),
        Float((byte) 4),
        Long((byte) 5),
        Double((byte) 6),
        NameAndType((byte) 12),
        UTF8((byte) 1),
        MethodHandle((byte) 15),
        MethodType((byte) 16),
        InvokeDynamic((byte) 18);

        private final byte value;

        Tag(final byte value) {
            this.value = value;
        }

        public byte getValue() {
            return value;
        }

        public static Tag getTag(final byte value) {
            for (final Tag tag : Tag.values()) {
                if (tag.value == value)
                    return tag;
            }
            throw new IllegalArgumentException();
        }

    }

    private Tag tag;
    private Info info;


    public ConstantPool(final Tag tag, final Info info) {
        this.tag = tag;
        this.info = info;
    }

    public static ConstantPool decode(ByteBuffer buffer) {
        Tag tag = Tag.getTag(buffer.get());
        Info info = switch (tag) {
            case Class: yield ClassInfo.decode(buffer);
            case FieldRef:
            case MethodRef:
            case InterfaceMethodRef:
                yield RefInfo.decode(tag, buffer);
            case String:
                yield StringInfo.decode(buffer);
            case Integer:
                yield IntegerInfo.decode(buffer);
            case Float:
                yield FloatInfo.decode(buffer);
            case Long:
                yield LongInfo.decode(buffer);
            case Double:
                yield DoubleInfo.decode(buffer);
            case NameAndType:
                yield NameTypeInfo.decode(buffer);
            case UTF8:
                yield UTF8Info.decode(buffer);
            case MethodHandle:
                yield MethodHandleInfo.decode(buffer);
            case MethodType:
                yield MethodTypeInfo.decode(buffer);
            case InvokeDynamic:
                yield InvokeDynamicInfo.decode(buffer);
        };
        return new ConstantPool(tag, info);
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.put(tag.getValue());
        info.encode(buffer);
    }
}
