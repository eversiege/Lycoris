package org.momento.lycoris.mixins.mixin.classe.structures.infos

import org.momento.lycoris.mixins.mixin.classe.ByteCodec;
import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;
import org.momento.lycoris.mixins.mixin.classe.structures.constants.ConstantInfo;
import org.momento.lycoris.mixins.mixin.classe.structures.constants.UTF8Info;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.Attribute;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.Code;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.ConstantValue;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.StackMapTable;

import java.nio.ByteBuffer;

public class AttributeInfo implements ByteCodec {

    private final char nameIndex;
    private final SizedByteCodec attribute;

    public AttributeInfo(final char nameIndex, final SizedByteCodec attribute) {
        this.nameIndex = nameIndex;
        this.attribute = attribute;
    }

    public static AttributeInfo decode(final ConstantPool[] constantPools, ByteBuffer buffer) {
        char nameIndex = buffer.getChar();
        ConstantInfo info = constantPools[nameIndex].getInfo();
        if (info == null || info.getTag() != ConstantPool.Tag.UTF8)
            throw new RuntimeException();
        String attributeName =  ((UTF8Info) info).getString();
        buffer.position(buffer.position() + 4);
        SizedByteCodec attribute = switch (attributeName) {
            case "ConstantValue" -> ConstantValue.decode(buffer);
            case "Code" -> Code.decode(constantPools, buffer);
            case "StackMapTable" -> StackMapTable.decode(buffer);
            case "LineNumberTable" -> null;
            case "LocalVariableTable" -> null;
            case "SourceFile" -> null;
            case "Deprecated" -> null;
            case "Synthetic" -> null;
            case "Exceptions" -> null;
            case "InnerClasses" -> null;
            case "EnclosingMethod" -> null;
            case "Signature" -> null;
            case "BoostrapMethods" -> null;
            default -> throw new IllegalStateException("Unexpected value: " + attributeName);
        };
        return new AttributeInfo(nameIndex, attribute);
    }

    public int getSize() { return 6 + attribute.getSize(); }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(nameIndex);
        if (attribute == null) {
            buffer.putInt(0);
            return;
        }
        buffer.putInt(attribute.getSize());
        attribute.encode(buffer);
    }
}
