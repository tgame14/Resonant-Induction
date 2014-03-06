package resonantinduction.archaic.fluid.gutter;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import resonantinduction.core.ResonantInduction;
import resonantinduction.core.fluid.IPressurizedNode;
import resonantinduction.core.fluid.TilePressurizedNode;
import universalelectricity.api.vector.Vector3;
import calclavia.lib.prefab.vector.Cuboid;
import calclavia.lib.utility.FluidUtility;
import calclavia.lib.utility.WorldUtility;

/**
 * The gutter, used for fluid transfer.
 * 
 * @author Calclavia
 * 
 */
public class TileGutter extends TilePressurizedNode implements IPressurizedNode
{
	public TileGutter()
	{
		super(Material.wood);
		textureName = "material_wood_surface";
		isOpaqueCube = false;
		normalRender = false;
	}

	@Override
	public Iterable<Cuboid> getCollisionBoxes()
	{
		List<Cuboid> list = new ArrayList<Cuboid>();

		float thickness = 0.1F;

		if (!WorldUtility.isEnabledSide(renderSides, ForgeDirection.DOWN))
		{
			list.add(new Cuboid(0.0F, 0.0F, 0.0F, 1.0F, thickness, 1.0F));
		}

		if (!WorldUtility.isEnabledSide(renderSides, ForgeDirection.WEST))
		{
			list.add(new Cuboid(0.0F, 0.0F, 0.0F, thickness, 1.0F, 1.0F));
		}
		if (!WorldUtility.isEnabledSide(renderSides, ForgeDirection.NORTH))
		{
			list.add(new Cuboid(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, thickness));
		}

		if (!WorldUtility.isEnabledSide(renderSides, ForgeDirection.EAST))
		{
			list.add(new Cuboid(1.0F - thickness, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F));
		}

		if (!WorldUtility.isEnabledSide(renderSides, ForgeDirection.SOUTH))
		{
			list.add(new Cuboid(0.0F, 0.0F, 1.0F - thickness, 1.0F, 1.0F, 1.0F));
		}

		return list;
	}

	@Override
	public void collide(Entity entity)
	{
		if (getInternalTank().getFluidAmount() > 0)
		{
			int pressure = getPressure(null);

			for (int i = 2; i < 6; i++)
			{
				ForgeDirection dir = ForgeDirection.getOrientation(i);
				Vector3 position = position().translate(dir);

				TileEntity checkTile = position.getTileEntity(world());

				if (checkTile instanceof TileGutter)
				{
					int deltaPressure = pressure - ((TileGutter) checkTile).getPressure(null);

					entity.motionX += 0.01 * dir.offsetX * deltaPressure;
					entity.motionY += 0.01 * dir.offsetY * deltaPressure;
					entity.motionZ += 0.01 * dir.offsetZ * deltaPressure;
				}
			}
		}

		if (entity instanceof EntityItem)
		{
			entity.noClip = true;
		}
	}

	@Override
	public boolean activate(EntityPlayer player, int side, Vector3 vector3)
	{
		if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ResonantInduction.itemDust)
		{
			return false;
		}

		if (!world().isRemote)
		{
			return FluidUtility.playerActivatedFluidItem(world(), x(), y(), z(), player, side);
		}

		return true;
	}

	@Override
	public void onFillRain()
	{
		if (!world().isRemote)
		{
			fill(ForgeDirection.UNKNOWN, new FluidStack(FluidRegistry.WATER, 10), true);
		}
	}

	@Override
	public void refresh()
	{
		/**
		 * Drain block above if it is a fluid.
		 */
		Vector3 drainPos = new Vector3(this).translate(0, 1, 0);
		FluidStack drain = FluidUtility.drainBlock(worldObj, drainPos, false);

		if (drain != null)
		{
			if (fill(ForgeDirection.UP, drain, true) > 0)
				FluidUtility.drainBlock(worldObj, drainPos, true);
		}

		super.refresh();
	}

	@Override
	public void validateConnectionSide(TileEntity tileEntity, ForgeDirection side)
	{
		if (!this.worldObj.isRemote)
		{
			if (tileEntity instanceof IFluidHandler)
			{
				if (tileEntity instanceof TileGutter)
				{
					getNetwork().merge(((TileGutter) tileEntity).getNetwork());
				}

				renderSides = WorldUtility.setEnableSide(renderSides, side, true);
				connectedBlocks[side.ordinal()] = tileEntity;
			}
		}
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{
		if (!resource.getFluid().isGaseous())
		{
			return super.fill(from, resource, doFill);
		}

		return 0;
	}

	@Override
	public int getPressure(ForgeDirection dir)
	{
		if (dir == ForgeDirection.UP)
			return -3;

		if (dir == ForgeDirection.DOWN)
			return +3;

		return pressure;
	}

	@Override
	public int getMaxFlowRate()
	{
		return 20;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid)
	{
		return from != ForgeDirection.UP && !fluid.isGaseous();
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid)
	{
		return from != ForgeDirection.UP && !fluid.isGaseous();
	}
}
