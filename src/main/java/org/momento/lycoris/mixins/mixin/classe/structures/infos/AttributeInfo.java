package org.momento.lycoris.mixins.mixin.classe.structures.infos

import org.momento.lycoris.mixins.mixin.classe.ByteCodec;
import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;
import org.momento.lycoris.mixins.mixin.classe.structures.constants.ConstantInfo;
import org.momento.lycoris.mixins.mixin.classe.structures.constants.UTF8Info;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.Attribute;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.ConstantValue;

import java.nio.ByteBuffer;

public class AttributeInfo implements ByteCodec {

    private final char nameIndex;
    private final Attribute attribute;

    public AttributeInfo(final char nameIndex, final Attribute attribute) {
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
        Attribute attribute = switch (attributeName) {
            case "ConstantValue" -> ConstantValue.decode(buffer);
            case "Code" ->
            case "LineNumberTable" ->
            case "LocalVariableTable" ->
            case "SourceFile" ->
            case "Deprecated" ->
            case "Synthetic" ->
            case "Exceptions" ->
            case "InnerClasses" ->
            case "EnclosingMethod" ->
            case "Signature" ->
            case "StackMapTable" ->
            case "BoostrapMethods"
        };
        return new AttributeInfo(nameIndex, attribute);
    }

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
