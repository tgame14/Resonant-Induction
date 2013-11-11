package dark.core.common.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import universalelectricity.core.item.ItemElectric;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dark.core.common.DarkMain;
import dark.core.interfaces.IExtraInfo.IExtraItemInfo;
import dark.core.prefab.ModPrefab;

/** Simple battery to store energy
 * 
 * @author DarkGuardsman */
public class ItemBattery extends ItemElectric implements IExtraItemInfo
{
    public ItemBattery()
    {
        super(DarkMain.CONFIGURATION.getItem("Battery", ModPrefab.getNextItemId()).getInt());
        this.setUnlocalizedName(DarkMain.getInstance().PREFIX + "Battery");
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return "item." + this.getUnlocalizedName();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        this.itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().replace("item.", ""));
    }

    @Override
    public float getVoltage(ItemStack itemStack)
    {
        return 25;
    }

    @Override
    public float getMaxElectricityStored(ItemStack theItem)
    {
        return 5000;
    }

    @Override
    public boolean hasExtraConfigs()
    {
        return false;
    }

    @Override
    public void loadExtraConfigs(Configuration config)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void loadOreNames()
    {
        OreDictionary.registerOre("Battery", this);
    }
}
