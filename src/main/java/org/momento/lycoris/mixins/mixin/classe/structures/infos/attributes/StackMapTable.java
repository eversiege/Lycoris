package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import org.momento.lycoris.mixins.mixin.classe.ByteCodec;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames.StackMapFrame;

import java.nio.ByteBuffer;

public class StackMapTable implements SizedByteCodec {

    private final StackMapFrame[] entries;

    public StackMapTable(final StackMapFrame[] entries) {
        this.entries = entries;
    }

    @Override
    public int getSize() {
        int baseSize = 2;
        for (StackMapFrame entry : entries)
            baseSize += entry.getSize();
        return baseSize;
    }

    public StackMapFrame[] getEntries() { return entries; }

    public static StackMapTable decode(ByteBuffer buffer) {
        StackMapFrame[] entries = new StackMapFrame[buffer.getChar()];
        for (int i = 0; i < entries.length; ++i) {
            byte frameType = buffer.get();
            entries[i] = switch (StackMapFrame.Type.fromValue(frameType)) {
                case SAME -> null;
                case SAME_LOCAL_1_STACk_ITEM -> null;
                case SAME_LOCAL_1_STACK_ITEM_EXTENDED -> null;
                case CHOP -> null;
                case SAME_FRAME_EXTENDED -> null;
                case APPEND -> null;
                case FULL_FRAME -> null;
            };
        }
        return new StackMapTable(entries);
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar((char) entries.length);
        for (StackMapFrame entry : entries)
            entry.encode(buffer);
    }
}
