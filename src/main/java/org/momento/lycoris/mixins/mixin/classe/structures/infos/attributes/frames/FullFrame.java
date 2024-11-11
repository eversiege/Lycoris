package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames;

public class FullFrame extends StackMapFrame {

    private final char offsetDelta;
    private final VerificationTypeInfo[] locals;
    private final VerificationTypeInfo[] stack;


    public FullFrame(final Type type, final char offsetDelta, final VerificationTypeInfo[] locals, final VerificationTypeInfo[] stack) {
        super(type);
        this.offsetDelta = offsetDelta;
        this.locals = locals;
        this.stack = stack;
    }

    public char getOffsetDelta() { return offsetDelta; }
    public VerificationTypeInfo[] getLocals() { return locals; }
    public VerificationTypeInfo[] getStack() { return stack; }
}
