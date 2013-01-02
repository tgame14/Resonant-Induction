// Date: 9/3/2012 6:12:15 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package liquidmechanics.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPump extends ModelBase
{
	// fields
	ModelRenderer Main;
	ModelRenderer sidePort;
	ModelRenderer FrontPort;
	ModelRenderer pivit;
	ModelRenderer crank;
	ModelRenderer Rope;
	ModelRenderer pivit2;
	ModelRenderer Piston;
	ModelRenderer pPlate;
	ModelRenderer Rope2;
	ModelRenderer BackPort;

	public ModelPump()
	{
		textureWidth = 128;
		textureHeight = 128;

		Main = new ModelRenderer(this, 0, 0);
		Main.addBox(-5F, 0F, -5F, 10, 13, 10);
		Main.setRotationPoint(1F, 11F, 0F);
		Main.setTextureSize(128, 128);
		Main.mirror = true;
		setRotation(Main, 0F, 0F, 0F);
		sidePort = new ModelRenderer(this, 0, 76);
		sidePort.addBox(-6F, -3F, -3F, 6, 6, 6);
		sidePort.setRotationPoint(8F, 16F, 0F);
		sidePort.setTextureSize(128, 128);
		sidePort.mirror = true;
		setRotation(sidePort, 0F, 0F, 0F);
		FrontPort = new ModelRenderer(this, 0, 63);
		FrontPort.addBox(-3F, -3F, 0F, 6, 6, 6);
		FrontPort.setRotationPoint(0F, 16F, -8F);
		FrontPort.setTextureSize(128, 128);
		FrontPort.mirror = true;
		setRotation(FrontPort, 0F, 0F, 0F);
		pivit = new ModelRenderer(this, 0, 40);
		pivit.addBox(-1F, 0F, -1F, 2, 3, 1);
		pivit.setRotationPoint(0F, 8F, 1F);
		pivit.setTextureSize(128, 128);
		pivit.mirror = true;
		setRotation(pivit, 0F, 0F, 0F);
		crank = new ModelRenderer(this, 48, 0);
		crank.addBox(-8F, 0F, -1F, 14, 2, 1);
		crank.setRotationPoint(0F, 7F, 0F);
		crank.setTextureSize(128, 128);
		crank.mirror = true;
		setRotation(crank, 0F, 0F, 0F);
		Rope = new ModelRenderer(this, 0, 28);
		Rope.addBox(0F, 0F, 0F, 1, 7, 1);
		Rope.setRotationPoint(4F, 8F, -1F);
		Rope.setTextureSize(128, 128);
		Rope.mirror = true;
		setRotation(Rope, 0F, 0F, 0F);
		pivit2 = new ModelRenderer(this, 0, 40);
		pivit2.addBox(-1F, 0F, -1F, 2, 3, 1);
		pivit2.setRotationPoint(0F, 8F, -1F);
		pivit2.setTextureSize(128, 128);
		pivit2.mirror = true;
		setRotation(pivit2, 0F, 0F, 0F);
		pPlate = new ModelRenderer(this, 34, 30);
		pPlate.addBox(0F, 0F, 0F, 1, 12, 1);
		pPlate.setRotationPoint(-5F, 12F, -1F);
		pPlate.setTextureSize(128, 128);
		pPlate.mirror = true;
		setRotation(pPlate, 0F, 0F, 0F);
		Piston = new ModelRenderer(this, 20, 30);
		Piston.addBox(0F, 0F, 0F, 3, 12, 3);
		Piston.setRotationPoint(-8F, 12F, -2F);
		Piston.setTextureSize(128, 128);
		Piston.mirror = true;
		setRotation(Piston, 0F, 0F, 0F);
		Rope2 = new ModelRenderer(this, 0, 28);
		Rope2.addBox(0F, 0F, 0F, 1, 7, 1);
		Rope2.setRotationPoint(-7F, 8F, -1F);
		Rope2.setTextureSize(128, 128);
		Rope2.mirror = true;
		setRotation(Rope2, 0F, 0F, 0F);
		BackPort = new ModelRenderer(this, 0, 50);
		BackPort.addBox(-3F, -3F, -6F, 6, 6, 6);
		BackPort.setRotationPoint(0F, 16F, 8F);
		BackPort.setTextureSize(128, 128);
		BackPort.mirror = true;
		setRotation(BackPort, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

	public void renderMain(float f5)
	{
		Main.render(f5);
		pivit.render(f5);
		crank.render(f5);
		Rope.render(f5);
		pivit2.render(f5);
		Piston.render(f5);
		pPlate.render(f5);
		Rope2.render(f5);
	}

	public void renderC1(float f5)
	{
		sidePort.render(f5);
	}

	public void renderC2(float f5)
	{
		FrontPort.render(f5);
	}

	public void renderC3(float f5)
	{
		BackPort.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
