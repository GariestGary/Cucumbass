package io.github.gariestgary.cucumbass.data;

import io.github.gariestgary.cucumbass.Cucumbass;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

/** One installed cucumber. Type, charges, and custom properties are deliberately extensible. */
public record InstalledCucumber(ResourceLocation typeId, int charges, CompoundTag properties) {
    public static final ResourceLocation BASIC_TYPE = ResourceLocation.fromNamespaceAndPath(Cucumbass.MOD_ID, "basic");

    public InstalledCucumber {
        if (charges < 1) throw new IllegalArgumentException("charges must be positive");
        properties = properties.copy();
    }

    public static InstalledCucumber basic() { return new InstalledCucumber(BASIC_TYPE, 1, new CompoundTag()); }
    @Override public CompoundTag properties() { return properties.copy(); }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putString("Type", typeId.toString());
        tag.putInt("Charges", charges);
        if (!properties.isEmpty()) tag.put("Properties", properties.copy());
        return tag;
    }

    public static InstalledCucumber load(CompoundTag tag) {
        ResourceLocation typeId = ResourceLocation.parse(tag.getString("Type"));
        int charges = Math.max(1, tag.getInt("Charges"));
        CompoundTag properties = tag.contains("Properties", CompoundTag.TAG_COMPOUND) ? tag.getCompound("Properties") : new CompoundTag();
        return new InstalledCucumber(typeId, charges, properties);
    }
}
