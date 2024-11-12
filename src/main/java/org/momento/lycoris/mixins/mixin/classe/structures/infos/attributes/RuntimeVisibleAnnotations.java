package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.values.*;
import org.momento.lycoris.utils.Pair;

import java.nio.ByteBuffer;

public class RuntimeVisibleAnnotations implements SizedByteCodec {

    public static class Annotation implements SizedByteCodec {

        private final char typeIndex;
        private final Pair<Character, ElementValue>[] pairs;

        public Annotation(final char typeIndex, final Pair<Character, ElementValue>[] pairs) {
            this.typeIndex = typeIndex;
            this.pairs = pairs;
        }

        public char getTypeIndex() { return typeIndex; }
        public Pair<Character, ElementValue>[] getPairs() { return pairs; }

        public static Annotation decode(ByteBuffer buffer) {
            char typeIndex = buffer.getChar();
            Pair<Character, ElementValue>[] pairs = new Pair[buffer.getChar()];
            for (int i = 0; i < pairs.length; i++) {
                char nameIndex = buffer.getChar();
                ElementValue.Tag tag = ElementValue.Tag.fromValue(buffer.get());
                ElementValue value = switch (tag) {
                    case BYTE, CHAR, DOUBLE, FLOAT, INT, LONG, SHORT, BOOLEAN, STRING -> ConstValue.decode(tag, buffer);
                    case ENUMCONSTANT -> EnumConstValue.decode(tag, buffer);
                    case CLASS -> ClassConstValue.decode(tag, buffer);
                    case ANNOTATION -> AnnotationValue.decode(tag, buffer);
                    case ARRAY -> ArrayValue.decode(tag, buffer);
                };
                pairs[i] = new Pair<>(nameIndex, value);
            }
            return new Annotation(typeIndex, pairs);
        }

        @Override
        public int getSize() {
            int baseSize = 4;
            for (Pair<Character, ElementValue> pair : pairs)
                baseSize += 2 + pair.getSecond().getSize();
            return baseSize;
        }

        @Override
        public void encode(ByteBuffer buffer) {
            buffer.putChar(typeIndex);
            for (Pair<Character, ElementValue> pair : pairs) {
                buffer.putChar(pair.getFirst());
                pair.getSecond().encode(buffer);
            }
        }
    }

    private final Annotation[] annotations;

    public RuntimeVisibleAnnotations(final Annotation[] annotations) {
        this.annotations = annotations;
    }

    public Annotation[] getAnnotations() { return annotations; }

    public static RuntimeVisibleAnnotations decode(ByteBuffer buffer) {
        final Annotation[] annotations = new Annotation[buffer.getChar()];
        for (int i = 0; i < annotations.length; i++) {
            annotations[i] = null;
        }
        return new RuntimeVisibleAnnotations(annotations);
    }

    @Override
    public int getSize() {
        int baseSize = 2;
        for (Annotation annotation : annotations)
            baseSize += annotation.getSize();
        return baseSize;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar((char) annotations.length);
        for (Annotation annotation : annotations)
            annotation.encode(buffer);
    }
}
