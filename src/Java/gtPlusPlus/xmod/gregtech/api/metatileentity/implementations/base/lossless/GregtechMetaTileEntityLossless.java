package gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.lossless;

import static gregtech.api.enums.GT_Values.GT;
import gregtech.api.interfaces.ITexture;
import gtPlusPlus.core.lib.CORE;

public abstract class GregtechMetaTileEntityLossless extends MetaTileEntityLossless {
	/**
	 * Value between [0 - 9] to describe the Tier of this Machine.
	 */
	public final byte mTier;
	
	/**
	 * A simple Description.
	 */
	public final String mDescription;
	
	/**
	 * Contains all Textures used by this Block.
	 */
	public final ITexture[][][] mTextures;
	
	public GregtechMetaTileEntityLossless(int aID, String aName, String aNameRegional, int aTier, int aInvSlotCount, String aDescription, ITexture... aTextures) {
		super(aID, aName, aNameRegional, aInvSlotCount);
		mTier = (byte)Math.max(0, Math.min(aTier, 9));
		mDescription = aDescription;
		
		// must always be the last call!
		if (GT.isClientSide()) mTextures = getTextureSet(aTextures); else mTextures = null;
	}
	
	public GregtechMetaTileEntityLossless(String aName, int aTier, int aInvSlotCount, String aDescription, ITexture[][][] aTextures) {
		super(aName, aInvSlotCount);
		mTier = (byte)aTier;
		mDescription = aDescription;
		mTextures = aTextures;
		
	}
	
	@Override
	public byte getTileEntityBaseType() {
		return (byte)(Math.min(3, mTier<=0?0:1+((mTier-1) / 4)));
	}
	
    @Override
	public long getInputTier() {
    	return mTier;
    }
    
    @Override
	public long getOutputTier() {
    	return mTier;
    }
    
	@Override
	public String[] getDescription() {
		return new String[] {mDescription, CORE.GT_Tooltip};
	}
	
	/**
	 * Used Client Side to get a Texture Set for this Block.
	 * Called after setting the Tier and the Description so that those two are accessible.
	 * @param aTextures is the optional Array you can give to the Constructor.
	 */
	public abstract ITexture[][][] getTextureSet(ITexture[] aTextures);
}