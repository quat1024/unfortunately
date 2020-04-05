package someones.modfest.mod.unfortunately.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import someones.modfest.mod.unfortunately.Unfortunately;

import java.util.List;
import java.util.Random;

public class CrystalBallBlock extends Block {
	public CrystalBallBlock(Settings settings) {
		super(settings);
	}
	
	private static final VoxelShape BASE = createCuboidShape(0, 0, 0, 16, 4, 16);
	private static final VoxelShape STEM = createCuboidShape(6, 2, 6, 10, 5, 10);
	private static final VoxelShape BALL = createCuboidShape(4, 5, 4, 12, 13, 12);
	
	private static final VoxelShape ALL = VoxelShapes.union(BASE, STEM, BALL);
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
		return ALL;
	}
	
	@Override
	public VoxelShape getRayTraceShape(BlockState state, BlockView view, BlockPos pos) {
		return ALL;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if(random.nextInt(2) == 0) {
			world.addParticle(
				ParticleTypes.BUBBLE_POP,
				//position
				pos.getX() + (random.nextDouble() * (6d / 16d)) + (4d / 16d),
				pos.getY() + (14d / 16d),
				pos.getZ() + (random.nextDouble() * (6d / 16d)) + (4d / 16d),
				//velocity
				0, 
				0,
				0
			);
		}
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void buildTooltip(ItemStack stack, BlockView view, List<Text> tooltip, TooltipContext options) {
		tooltip.add(new TranslatableText(Unfortunately.MODID + ".tooltip.crystal_ball"));
	}
}
