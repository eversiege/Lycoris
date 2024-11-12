package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.values;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.RuntimeVisibleAnnotations;

import java.nio.ByteBuffer;

public class AnnotationValue extends ElementValue {

    private final RuntimeVisibleAnnotations.Annotation value;

    public AnnotationValue(final Tag tag, final RuntimeVisibleAnnotations.Annotation value) {
        super(tag);
        this.value = value;
    }

    public RuntimeVisibleAnnotations.Annotation getValue() { return value; }

    public static AnnotationValue decode(final Tag tag, ByteBuffer buffer) {
        return new AnnotationValue(tag, RuntimeVisibleAnnotations.Annotation.decode(buffer));
    }

    @Override
    public int getSize() {
        return super.getSize() + value.getSize();
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        value.encode(buffer);
    }
}
