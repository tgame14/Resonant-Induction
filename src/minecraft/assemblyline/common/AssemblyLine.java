package assemblyline.common;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.prefab.TranslationHelper;
import universalelectricity.prefab.UpdateNotifier;
import universalelectricity.prefab.multiblock.BlockMulti;
import universalelectricity.prefab.network.PacketManager;
import assemblyline.common.block.BlockCrate;
import assemblyline.common.block.ItemBlockCrate;
import assemblyline.common.machine.BlockManipulator;
import assemblyline.common.machine.BlockRejector;
import assemblyline.common.machine.armbot.BlockArmbot;
import assemblyline.common.machine.belt.BlockConveyorBelt;
import assemblyline.common.machine.crane.BlockCraneController;
import assemblyline.common.machine.crane.BlockCraneParts;
import assemblyline.common.machine.crane.ItemCraneParts;
import assemblyline.common.machine.detector.BlockDetector;
import assemblyline.common.machine.encoder.BlockEncoder;
import assemblyline.common.machine.encoder.ItemDisk;
import assemblyline.common.machine.imprinter.BlockImprinter;
import assemblyline.common.machine.imprinter.ItemImprinter;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarted;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = AssemblyLine.CHANNEL, name = AssemblyLine.NAME, version = AssemblyLine.VERSION, dependencies = "after:BasicComponents")
@NetworkMod(channels = { AssemblyLine.CHANNEL }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketManager.class)
public class AssemblyLine
{
	@SidedProxy(clientSide = "assemblyline.client.ClientProxy", serverSide = "assemblyline.common.CommonProxy")
	public static CommonProxy proxy;

	@Instance(AssemblyLine.CHANNEL)
	public static AssemblyLine instance;

	public static final String NAME = "Assembly Line";

	public static final String VERSION = "0.2.5";

	public static final String CHANNEL = "AssemblyLine";

	public static final String DIRECTORY_NO_SLASH = "assemblyline/";
	public static final String DIRECTORY = "/" + DIRECTORY_NO_SLASH;
	public static final String TEXTURE_PATH = DIRECTORY + "textures/";
	public static final String LANGUAGE_PATH = DIRECTORY + "language/";
	public static final String BLOCK_TEXTURE_PATH = TEXTURE_PATH + "blocks.png";
	public static final String ITEM_TEXTURE_PATH = TEXTURE_PATH + "items.png";

	private static final String[] LANGUAGES_SUPPORTED = new String[] { "en_US" };

	public static final Configuration CONFIGURATION = new Configuration(new File(Loader.instance().getConfigDir(), "UniversalElectricity/AssemblyLine.cfg"));

	public static final int BLOCK_ID_PREFIX = 3030;

	public static Block blockConveyorBelt;
	public static Block blockManipulator;
	public static Block blockCrate;
	public static Block blockImprinter;
	public static Block blockEncoder;
	public static Block blockDetector;
	public static Block blockRejector;
	public static Block blockArmbot;
	public static Block blockCraneController;
	public static Block blockCraneParts;

	public static BlockMulti blockMulti;

	public static final int ITEM_ID_PREFIX = 13030;
	public static Item itemImprint;
	public static Item itemDisk;

	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		UniversalElectricity.register(this, 1, 2, 4, false);
		instance = this;

		CONFIGURATION.load();
		blockConveyorBelt = new BlockConveyorBelt(CONFIGURATION.getBlock("Conveyor Belt", BLOCK_ID_PREFIX).getInt());
		blockManipulator = new BlockManipulator(CONFIGURATION.getBlock("Manipulator", BLOCK_ID_PREFIX + 1).getInt());
		blockCrate = new BlockCrate(CONFIGURATION.getBlock("Crate", BLOCK_ID_PREFIX + 3).getInt(), 0);
		blockImprinter = new BlockImprinter(CONFIGURATION.getBlock("Imprinter", BLOCK_ID_PREFIX + 4).getInt(), 4);
		blockDetector = new BlockDetector(CONFIGURATION.getBlock("Detector", BLOCK_ID_PREFIX + 5).getInt(), 1);
		blockRejector = new BlockRejector(CONFIGURATION.getBlock("Rejector", BLOCK_ID_PREFIX + 6).getInt());
		blockEncoder = new BlockEncoder(CONFIGURATION.getBlock("Encoder", BLOCK_ID_PREFIX + 7).getInt(), 7);
		blockArmbot = new BlockArmbot(CONFIGURATION.getBlock("Armbot", BLOCK_ID_PREFIX + 8).getInt());
		blockMulti = new BlockMulti(CONFIGURATION.getBlock("Multiblock", BLOCK_ID_PREFIX + 9).getInt());
		blockCraneController = new BlockCraneController(CONFIGURATION.getBlock("CraneController", BLOCK_ID_PREFIX + 10).getInt());
		blockCraneParts = new BlockCraneParts(CONFIGURATION.getBlock("CraneParts", BLOCK_ID_PREFIX + 11).getInt());

		itemImprint = new ItemImprinter(CONFIGURATION.getItem("Imprint", ITEM_ID_PREFIX).getInt());
		itemDisk = new ItemDisk(CONFIGURATION.getItem("Disk", ITEM_ID_PREFIX + 1).getInt());
		CONFIGURATION.save();

		NetworkRegistry.instance().registerGuiHandler(this, this.proxy);
		GameRegistry.registerBlock(blockConveyorBelt, "Conveyor Belt");
		GameRegistry.registerBlock(blockCrate, ItemBlockCrate.class, "Crate");
		GameRegistry.registerBlock(blockManipulator, "Manipulator");
		GameRegistry.registerBlock(blockImprinter, "Imprinter");
		GameRegistry.registerBlock(blockEncoder, "Encoder");
		GameRegistry.registerBlock(blockDetector, "Detector");
		GameRegistry.registerBlock(blockRejector, "Rejector");
		GameRegistry.registerBlock(blockArmbot, "Armbot");
		GameRegistry.registerBlock(blockCraneController, "Crane Controller");
		GameRegistry.registerBlock(blockCraneParts, ItemCraneParts.class, "Crane Parts");

		TabAssemblyLine.itemStack = new ItemStack(AssemblyLine.blockConveyorBelt);
		UpdateNotifier.INSTANCE.checkUpdate(NAME, VERSION, "http://calclavia.com/downloads/al/recommendedversion.txt");

		proxy.preInit();
	}

	@Init
	public void load(FMLInitializationEvent evt)
	{
		proxy.init();

		System.out.println(NAME + " Loaded: " + TranslationHelper.loadLanguages(LANGUAGE_PATH, LANGUAGES_SUPPORTED) + " languages.");

		// Armbot
		GameRegistry.addRecipe(new ShapedOreRecipe(blockArmbot, new Object[] { "II ", "SIS", "MCM", 'S', "plateSteel", 'C', "advancedCircuit", 'I', "ingotSteel", 'M', "motor" }));

		// Disk
		GameRegistry.addRecipe(new ShapedOreRecipe(itemDisk, new Object[] { "III", "ICI", "III", 'I', itemImprint, 'C', "advancedCircuit" }));

		// Encoder
		GameRegistry.addRecipe(new ShapedOreRecipe(blockEncoder, new Object[] { "S S", "SCS", "SSS", 'S', "ingotSteel", 'C', "advancedCircuit" }));

		// Imprint
		GameRegistry.addRecipe(new ShapedOreRecipe(itemImprint, new Object[] { "R", "P", "I", 'P', Item.paper, 'R', Item.redstone, 'I', new ItemStack(Item.dyePowder, 1, 0) }));

		// Imprinter
		GameRegistry.addRecipe(new ShapedOreRecipe(blockImprinter, new Object[] { "SIS", "SPS", "WWW", 'S', "ingotSteel", 'W', Block.wood, 'P', Block.pistonStickyBase, 'I', new ItemStack(Item.dyePowder, 1, 0) }));

		// Detector
		GameRegistry.addRecipe(new ShapedOreRecipe(blockDetector, new Object[] { "SES", "SCS", "SPS", 'S', "ingotSteel", 'C', "basicCircuit", 'E', Item.eyeOfEnder }));

		// Crate
		GameRegistry.addRecipe(new ShapedOreRecipe(blockCrate, new Object[] { "TST", "S S", "TST", 'S', "ingotSteel", 'T', Item.stick }));

		// Conveyor Belt
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockConveyorBelt, 10), new Object[] { "III", "WMW", 'I', "ingotSteel", 'W', Block.wood, 'M', "motor" }));

		// Rejector
		GameRegistry.addRecipe(new ShapedOreRecipe(blockRejector, new Object[] { "WPW", "@R@", '@', "plateSteel", 'R', Item.redstone, 'P', Block.pistonBase, 'C', "basicCircuit", 'W', "copperWire" }));

		// Manipulator
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(blockManipulator, 2), new Object[] { Block.dispenser, "basicCircuit" }));
	}

	@ServerStarted
	public void serverStarted(FMLServerStartedEvent evt)
	{
		// LOLOLOL i wish
		// MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().remove("briman0094");
	}

	public static void printSidedData(String data)
	{
		System.out.print(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT ? "[C]" : "[S]");
		System.out.println(" " + data);
	}
}
