package someones.modfest.mod.unfortunately.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

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
}
