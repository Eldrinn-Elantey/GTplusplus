package gtPlusPlus.preloader.asm.transformers;

import static org.objectweb.asm.Opcodes.*;

import cpw.mods.fml.relauncher.FMLRelaunchLog;
import gtPlusPlus.preloader.DevHelper;
import org.apache.logging.log4j.Level;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class ClassTransformer_GC_FuelLoader {

    // The qualified name of the class we plan to transform.
    private static final String className = "micdoodle8.mods.galacticraft.core.tile.TileEntityFuelLoader";
    // micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader

    private final boolean isValid;
    private final ClassReader reader;
    private final ClassWriter writer;
    private final boolean isObfuscated;

    public ClassTransformer_GC_FuelLoader(byte[] basicClass, boolean obfuscated) {

        ClassReader aTempReader = null;
        ClassWriter aTempWriter = null;

        isObfuscated = obfuscated;

        aTempReader = new ClassReader(basicClass);
        aTempWriter = new ClassWriter(aTempReader, ClassWriter.COMPUTE_FRAMES);
        aTempReader.accept(new localClassVisitor(aTempWriter), 0);

        if (aTempReader != null && aTempWriter != null) {
            isValid = true;
        } else {
            isValid = false;
        }
        reader = aTempReader;
        writer = aTempWriter;

        if (reader != null && writer != null) {
            injectMethod();
        } else {
            FMLRelaunchLog.log("[GT++ ASM] Galacticraft Fuel_Loader Patch", Level.INFO, "Failed to Inject new code.");
        }
    }

    public boolean isValidTransformer() {
        return isValid;
    }

    public ClassReader getReader() {
        return reader;
    }

    public ClassWriter getWriter() {
        return writer;
    }

    public void injectMethod() {
        String aWorld =
                isObfuscated ? DevHelper.getObfuscated("net/minecraft/world/World") : "net/minecraft/world/World";
        String aItemStack =
                isObfuscated ? DevHelper.getObfuscated("net/minecraft/item/ItemStack") : "net/minecraft/item/ItemStack";
        String aTileEntity = isObfuscated
                ? DevHelper.getObfuscated("net/minecraft/tileentity/TileEntity")
                : "net/minecraft/tileentity/TileEntity";

        if (isValidTransformer()) {
            FMLRelaunchLog.log(
                    "[GT++ ASM] Galacticraft Fuel_Loader Patch",
                    Level.INFO,
                    "Injecting updateEntity into " + className + ".");
            MethodVisitor mv = getWriter().visitMethod(ACC_PUBLIC, "updateEntity", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(60, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(
                    INVOKESPECIAL,
                    "micdoodle8/mods/galacticraft/core/energy/tile/TileBaseElectricBlockWithInventory",
                    "updateEntity",
                    "()V",
                    false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(61, l1);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "worldObj",
                    "L" + aWorld + ";");
            mv.visitFieldInsn(GETFIELD, "" + aWorld + "", "isRemote", "Z");
            Label l2 = new Label();
            mv.visitJumpInsn(IFNE, l2);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLineNumber(62, l3);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitInsn(ICONST_0);
            mv.visitFieldInsn(
                    PUTFIELD, "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader", "loadedFuelLastTick", "Z");
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLineNumber(63, l4);
            mv.visitInsn(ACONST_NULL);
            mv.visitVarInsn(ASTORE, 1);
            Label l5 = new Label();
            mv.visitLabel(l5);
            mv.visitLineNumber(66, l5);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "containingItems",
                    "[L" + aItemStack + ";");
            mv.visitInsn(ICONST_1);
            mv.visitInsn(AALOAD);
            Label l6 = new Label();
            mv.visitJumpInsn(IFNULL, l6);
            Label l7 = new Label();
            mv.visitLabel(l7);
            mv.visitLineNumber(67, l7);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "containingItems",
                    "[L" + aItemStack + ";");
            mv.visitInsn(ICONST_1);
            mv.visitInsn(AALOAD);
            mv.visitMethodInsn(
                    INVOKESTATIC,
                    "net/minecraftforge/fluids/FluidContainerRegistry",
                    "getFluidForFilledItem",
                    "(L" + aItemStack + ";)Lnet/minecraftforge/fluids/FluidStack;",
                    false);
            mv.visitVarInsn(ASTORE, 1);
            Label l8 = new Label();
            mv.visitLabel(l8);
            mv.visitLineNumber(68, l8);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitJumpInsn(IFNULL, l6);
            Label l9 = new Label();
            mv.visitLabel(l9);
            mv.visitLineNumber(69, l9);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(
                    INVOKESTATIC,
                    "net/minecraftforge/fluids/FluidRegistry",
                    "getFluidName",
                    "(Lnet/minecraftforge/fluids/FluidStack;)Ljava/lang/String;",
                    false);
            mv.visitMethodInsn(
                    INVOKESTATIC,
                    "micdoodle8/mods/galacticraft/core/util/FluidUtil",
                    "testFuel",
                    "(Ljava/lang/String;)Z",
                    false);
            mv.visitVarInsn(ISTORE, 4);
            Label l10 = new Label();
            mv.visitLabel(l10);
            mv.visitLineNumber(70, l10);
            mv.visitVarInsn(ILOAD, 4);
            mv.visitJumpInsn(IFEQ, l6);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "fuelTank",
                    "Lnet/minecraftforge/fluids/FluidTank;");
            mv.visitMethodInsn(
                    INVOKEVIRTUAL,
                    "net/minecraftforge/fluids/FluidTank",
                    "getFluid",
                    "()Lnet/minecraftforge/fluids/FluidStack;",
                    false);
            Label l11 = new Label();
            mv.visitJumpInsn(IFNULL, l11);
            Label l12 = new Label();
            mv.visitLabel(l12);
            mv.visitLineNumber(71, l12);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "fuelTank",
                    "Lnet/minecraftforge/fluids/FluidTank;");
            mv.visitMethodInsn(
                    INVOKEVIRTUAL,
                    "net/minecraftforge/fluids/FluidTank",
                    "getFluid",
                    "()Lnet/minecraftforge/fluids/FluidStack;",
                    false);
            mv.visitFieldInsn(GETFIELD, "net/minecraftforge/fluids/FluidStack", "amount", "I");
            mv.visitVarInsn(ALOAD, 1);
            mv.visitFieldInsn(GETFIELD, "net/minecraftforge/fluids/FluidStack", "amount", "I");
            mv.visitInsn(IADD);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "fuelTank",
                    "Lnet/minecraftforge/fluids/FluidTank;");
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraftforge/fluids/FluidTank", "getCapacity", "()I", false);
            mv.visitJumpInsn(IF_ICMPGT, l6);
            mv.visitLabel(l11);
            mv.visitLineNumber(73, l11);
            mv.visitFrame(
                    F_FULL,
                    5,
                    new Object[] {
                        "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                        "net/minecraftforge/fluids/FluidStack",
                        TOP,
                        TOP,
                        INTEGER
                    },
                    0,
                    new Object[] {});
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "fuelTank",
                    "Lnet/minecraftforge/fluids/FluidTank;");
            mv.visitMethodInsn(
                    INVOKEVIRTUAL,
                    "net/minecraftforge/fluids/FluidTank",
                    "getFluid",
                    "()Lnet/minecraftforge/fluids/FluidStack;",
                    false);
            mv.visitVarInsn(ASTORE, 5);
            Label l13 = new Label();
            mv.visitLabel(l13);
            mv.visitLineNumber(74, l13);
            mv.visitInsn(ICONST_0);
            mv.visitVarInsn(ISTORE, 6);
            Label l14 = new Label();
            mv.visitLabel(l14);
            mv.visitLineNumber(75, l14);
            mv.visitVarInsn(ALOAD, 5);
            Label l15 = new Label();
            mv.visitJumpInsn(IFNONNULL, l15);
            Label l16 = new Label();
            mv.visitLabel(l16);
            mv.visitLineNumber(76, l16);
            mv.visitFieldInsn(
                    GETSTATIC,
                    "gtPlusPlus/core/item/chemistry/RocketFuels",
                    "mValidRocketFuels",
                    "Ljava/util/HashMap;");
            Label l17 = new Label();
            mv.visitLabel(l17);
            mv.visitLineNumber(77, l17);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/HashMap", "values", "()Ljava/util/Collection;", false);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Collection", "iterator", "()Ljava/util/Iterator;", true);
            mv.visitVarInsn(ASTORE, 8);
            Label l18 = new Label();
            mv.visitJumpInsn(GOTO, l18);
            Label l19 = new Label();
            mv.visitLabel(l19);
            mv.visitFrame(
                    F_FULL,
                    9,
                    new Object[] {
                        "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                        "net/minecraftforge/fluids/FluidStack",
                        TOP,
                        TOP,
                        INTEGER,
                        "net/minecraftforge/fluids/FluidStack",
                        INTEGER,
                        TOP,
                        "java/util/Iterator"
                    },
                    0,
                    new Object[] {});
            mv.visitVarInsn(ALOAD, 8);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
            mv.visitTypeInsn(CHECKCAST, "net/minecraftforge/fluids/Fluid");
            mv.visitVarInsn(ASTORE, 7);
            Label l20 = new Label();
            mv.visitLabel(l20);
            mv.visitLineNumber(78, l20);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(
                    INVOKEVIRTUAL,
                    "net/minecraftforge/fluids/FluidStack",
                    "getFluid",
                    "()Lnet/minecraftforge/fluids/Fluid;",
                    false);
            mv.visitVarInsn(ALOAD, 7);
            mv.visitJumpInsn(IF_ACMPNE, l18);
            Label l21 = new Label();
            mv.visitLabel(l21);
            mv.visitLineNumber(79, l21);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "fuelTank",
                    "Lnet/minecraftforge/fluids/FluidTank;");
            mv.visitTypeInsn(NEW, "net/minecraftforge/fluids/FluidStack");
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 7);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitFieldInsn(GETFIELD, "net/minecraftforge/fluids/FluidStack", "amount", "I");
            mv.visitMethodInsn(
                    INVOKESPECIAL,
                    "net/minecraftforge/fluids/FluidStack",
                    "<init>",
                    "(Lnet/minecraftforge/fluids/Fluid;I)V",
                    false);
            mv.visitInsn(ICONST_1);
            mv.visitMethodInsn(
                    INVOKEVIRTUAL,
                    "net/minecraftforge/fluids/FluidTank",
                    "fill",
                    "(Lnet/minecraftforge/fluids/FluidStack;Z)I",
                    false);
            Label l22 = new Label();
            mv.visitJumpInsn(IFLE, l22);
            mv.visitInsn(ICONST_1);
            Label l23 = new Label();
            mv.visitJumpInsn(GOTO, l23);
            mv.visitLabel(l22);
            mv.visitFrame(
                    F_FULL,
                    9,
                    new Object[] {
                        "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                        "net/minecraftforge/fluids/FluidStack",
                        TOP,
                        TOP,
                        INTEGER,
                        "net/minecraftforge/fluids/FluidStack",
                        INTEGER,
                        "net/minecraftforge/fluids/Fluid",
                        "java/util/Iterator"
                    },
                    0,
                    new Object[] {});
            mv.visitInsn(ICONST_0);
            mv.visitLabel(l23);
            mv.visitFrame(F_SAME1, 0, null, 1, new Object[] {INTEGER});
            mv.visitVarInsn(ISTORE, 6);
            mv.visitLabel(l18);
            mv.visitLineNumber(76, l18);
            mv.visitFrame(
                    F_FULL,
                    9,
                    new Object[] {
                        "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                        "net/minecraftforge/fluids/FluidStack",
                        TOP,
                        TOP,
                        INTEGER,
                        "net/minecraftforge/fluids/FluidStack",
                        INTEGER,
                        TOP,
                        "java/util/Iterator"
                    },
                    0,
                    new Object[] {});
            mv.visitVarInsn(ALOAD, 8);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
            mv.visitJumpInsn(IFNE, l19);
            Label l24 = new Label();
            mv.visitLabel(l24);
            mv.visitLineNumber(82, l24);
            Label l25 = new Label();
            mv.visitJumpInsn(GOTO, l25);
            mv.visitLabel(l15);
            mv.visitLineNumber(84, l15);
            mv.visitFrame(
                    F_FULL,
                    7,
                    new Object[] {
                        "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                        "net/minecraftforge/fluids/FluidStack",
                        TOP,
                        TOP,
                        INTEGER,
                        "net/minecraftforge/fluids/FluidStack",
                        INTEGER
                    },
                    0,
                    new Object[] {});
            mv.visitVarInsn(ALOAD, 5);
            mv.visitFieldInsn(GETFIELD, "net/minecraftforge/fluids/FluidStack", "amount", "I");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "fuelTank",
                    "Lnet/minecraftforge/fluids/FluidTank;");
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraftforge/fluids/FluidTank", "getCapacity", "()I", false);
            mv.visitJumpInsn(IF_ICMPGE, l25);
            mv.visitVarInsn(ALOAD, 5);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(
                    INVOKEVIRTUAL,
                    "net/minecraftforge/fluids/FluidStack",
                    "isFluidEqual",
                    "(Lnet/minecraftforge/fluids/FluidStack;)Z",
                    false);
            mv.visitJumpInsn(IFEQ, l25);
            Label l26 = new Label();
            mv.visitLabel(l26);
            mv.visitLineNumber(85, l26);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "fuelTank",
                    "Lnet/minecraftforge/fluids/FluidTank;");
            mv.visitTypeInsn(NEW, "net/minecraftforge/fluids/FluidStack");
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 5);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitFieldInsn(GETFIELD, "net/minecraftforge/fluids/FluidStack", "amount", "I");
            mv.visitMethodInsn(
                    INVOKESPECIAL,
                    "net/minecraftforge/fluids/FluidStack",
                    "<init>",
                    "(Lnet/minecraftforge/fluids/FluidStack;I)V",
                    false);
            mv.visitInsn(ICONST_1);
            mv.visitMethodInsn(
                    INVOKEVIRTUAL,
                    "net/minecraftforge/fluids/FluidTank",
                    "fill",
                    "(Lnet/minecraftforge/fluids/FluidStack;Z)I",
                    false);
            Label l27 = new Label();
            mv.visitJumpInsn(IFLE, l27);
            mv.visitInsn(ICONST_1);
            Label l28 = new Label();
            mv.visitJumpInsn(GOTO, l28);
            mv.visitLabel(l27);
            mv.visitFrame(F_SAME, 0, null, 0, null);
            mv.visitInsn(ICONST_0);
            mv.visitLabel(l28);
            mv.visitFrame(F_SAME1, 0, null, 1, new Object[] {INTEGER});
            mv.visitVarInsn(ISTORE, 6);
            mv.visitLabel(l25);
            mv.visitLineNumber(88, l25);
            mv.visitFrame(F_SAME, 0, null, 0, null);
            mv.visitVarInsn(ILOAD, 6);
            mv.visitJumpInsn(IFEQ, l6);
            Label l29 = new Label();
            mv.visitLabel(l29);
            mv.visitLineNumber(89, l29);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "containingItems",
                    "[L" + aItemStack + ";");
            mv.visitInsn(ICONST_1);
            mv.visitInsn(AALOAD);
            mv.visitInsn(DUP);
            mv.visitFieldInsn(GETFIELD, "" + aItemStack + "", "stackSize", "I");
            mv.visitInsn(ICONST_1);
            mv.visitInsn(ISUB);
            mv.visitFieldInsn(PUTFIELD, "" + aItemStack + "", "stackSize", "I");
            Label l30 = new Label();
            mv.visitLabel(l30);
            mv.visitLineNumber(90, l30);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "containingItems",
                    "[L" + aItemStack + ";");
            mv.visitInsn(ICONST_1);
            mv.visitInsn(AALOAD);
            mv.visitFieldInsn(GETFIELD, "" + aItemStack + "", "stackSize", "I");
            mv.visitJumpInsn(IFNE, l6);
            Label l31 = new Label();
            mv.visitLabel(l31);
            mv.visitLineNumber(91, l31);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "containingItems",
                    "[L" + aItemStack + ";");
            mv.visitInsn(ICONST_1);
            mv.visitInsn(ACONST_NULL);
            mv.visitInsn(AASTORE);
            mv.visitLabel(l6);
            mv.visitLineNumber(97, l6);
            mv.visitFrame(
                    F_FULL,
                    2,
                    new Object[] {
                        "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                        "net/minecraftforge/fluids/FluidStack"
                    },
                    0,
                    new Object[] {});
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader", "ticks", "I");
            mv.visitIntInsn(BIPUSH, 100);
            mv.visitInsn(IREM);
            Label l32 = new Label();
            mv.visitJumpInsn(IFNE, l32);
            Label l33 = new Label();
            mv.visitLabel(l33);
            mv.visitLineNumber(98, l33);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitInsn(ACONST_NULL);
            mv.visitFieldInsn(
                    PUTFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "attachedFuelable",
                    "Lmicdoodle8/mods/galacticraft/api/entity/IFuelable;");
            Label l34 = new Label();
            mv.visitLabel(l34);
            mv.visitLineNumber(99, l34);
            mv.visitFieldInsn(
                    GETSTATIC,
                    "net/minecraftforge/common/util/ForgeDirection",
                    "VALID_DIRECTIONS",
                    "[Lnet/minecraftforge/common/util/ForgeDirection;");
            mv.visitVarInsn(ASTORE, 4);
            Label l35 = new Label();
            mv.visitLabel(l35);
            mv.visitLineNumber(100, l35);
            mv.visitVarInsn(ALOAD, 4);
            mv.visitInsn(ARRAYLENGTH);
            mv.visitVarInsn(ISTORE, 3);
            Label l36 = new Label();
            mv.visitLabel(l36);
            mv.visitLineNumber(102, l36);
            mv.visitInsn(ICONST_0);
            mv.visitVarInsn(ISTORE, 2);
            Label l37 = new Label();
            mv.visitLabel(l37);
            Label l38 = new Label();
            mv.visitJumpInsn(GOTO, l38);
            Label l39 = new Label();
            mv.visitLabel(l39);
            mv.visitLineNumber(103, l39);
            mv.visitFrame(
                    F_APPEND,
                    3,
                    new Object[] {INTEGER, INTEGER, "[Lnet/minecraftforge/common/util/ForgeDirection;"},
                    0,
                    null);
            mv.visitVarInsn(ALOAD, 4);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitInsn(AALOAD);
            mv.visitVarInsn(ASTORE, 5);
            Label l40 = new Label();
            mv.visitLabel(l40);
            mv.visitLineNumber(104, l40);
            mv.visitTypeInsn(NEW, "micdoodle8/mods/galacticraft/api/vector/BlockVec3");
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(
                    INVOKESPECIAL,
                    "micdoodle8/mods/galacticraft/api/vector/BlockVec3",
                    "<init>",
                    "(L" + aTileEntity + ";)V",
                    false);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "worldObj",
                    "L" + aWorld + ";");
            mv.visitVarInsn(ALOAD, 5);
            mv.visitMethodInsn(
                    INVOKEVIRTUAL,
                    "micdoodle8/mods/galacticraft/api/vector/BlockVec3",
                    "getTileEntityOnSide",
                    "(L" + aWorld + ";Lnet/minecraftforge/common/util/ForgeDirection;)L" + aTileEntity + ";",
                    false);
            mv.visitVarInsn(ASTORE, 6);
            Label l41 = new Label();
            mv.visitLabel(l41);
            mv.visitLineNumber(105, l41);
            mv.visitVarInsn(ALOAD, 6);
            mv.visitTypeInsn(INSTANCEOF, "micdoodle8/mods/galacticraft/core/tile/TileEntityMulti");
            Label l42 = new Label();
            mv.visitJumpInsn(IFEQ, l42);
            Label l43 = new Label();
            mv.visitLabel(l43);
            mv.visitLineNumber(106, l43);
            mv.visitVarInsn(ALOAD, 6);
            mv.visitTypeInsn(CHECKCAST, "micdoodle8/mods/galacticraft/core/tile/TileEntityMulti");
            mv.visitMethodInsn(
                    INVOKEVIRTUAL,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityMulti",
                    "getMainBlockTile",
                    "()L" + aTileEntity + ";",
                    false);
            mv.visitVarInsn(ASTORE, 7);
            Label l44 = new Label();
            mv.visitLabel(l44);
            mv.visitLineNumber(107, l44);
            mv.visitVarInsn(ALOAD, 7);
            mv.visitTypeInsn(INSTANCEOF, "micdoodle8/mods/galacticraft/api/entity/IFuelable");
            Label l45 = new Label();
            mv.visitJumpInsn(IFEQ, l45);
            Label l46 = new Label();
            mv.visitLabel(l46);
            mv.visitLineNumber(108, l46);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 7);
            mv.visitTypeInsn(CHECKCAST, "micdoodle8/mods/galacticraft/api/entity/IFuelable");
            mv.visitFieldInsn(
                    PUTFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "attachedFuelable",
                    "Lmicdoodle8/mods/galacticraft/api/entity/IFuelable;");
            Label l47 = new Label();
            mv.visitLabel(l47);
            mv.visitLineNumber(109, l47);
            mv.visitJumpInsn(GOTO, l32);
            mv.visitLabel(l42);
            mv.visitLineNumber(111, l42);
            mv.visitFrame(
                    F_APPEND,
                    2,
                    new Object[] {"net/minecraftforge/common/util/ForgeDirection", "" + aTileEntity + ""},
                    0,
                    null);
            mv.visitVarInsn(ALOAD, 6);
            mv.visitTypeInsn(INSTANCEOF, "micdoodle8/mods/galacticraft/api/entity/IFuelable");
            mv.visitJumpInsn(IFEQ, l45);
            Label l48 = new Label();
            mv.visitLabel(l48);
            mv.visitLineNumber(112, l48);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 6);
            mv.visitTypeInsn(CHECKCAST, "micdoodle8/mods/galacticraft/api/entity/IFuelable");
            mv.visitFieldInsn(
                    PUTFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "attachedFuelable",
                    "Lmicdoodle8/mods/galacticraft/api/entity/IFuelable;");
            Label l49 = new Label();
            mv.visitLabel(l49);
            mv.visitLineNumber(113, l49);
            mv.visitJumpInsn(GOTO, l32);
            mv.visitLabel(l45);
            mv.visitLineNumber(102, l45);
            mv.visitFrame(F_CHOP, 2, null, 0, null);
            mv.visitIincInsn(2, 1);
            mv.visitLabel(l38);
            mv.visitFrame(F_SAME, 0, null, 0, null);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitJumpInsn(IF_ICMPLT, l39);
            mv.visitLabel(l32);
            mv.visitLineNumber(117, l32);
            mv.visitFrame(F_CHOP, 3, null, 0, null);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "fuelTank",
                    "Lnet/minecraftforge/fluids/FluidTank;");
            mv.visitJumpInsn(IFNULL, l2);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "fuelTank",
                    "Lnet/minecraftforge/fluids/FluidTank;");
            mv.visitMethodInsn(
                    INVOKEVIRTUAL,
                    "net/minecraftforge/fluids/FluidTank",
                    "getFluid",
                    "()Lnet/minecraftforge/fluids/FluidStack;",
                    false);
            mv.visitJumpInsn(IFNULL, l2);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "fuelTank",
                    "Lnet/minecraftforge/fluids/FluidTank;");
            mv.visitMethodInsn(
                    INVOKEVIRTUAL,
                    "net/minecraftforge/fluids/FluidTank",
                    "getFluid",
                    "()Lnet/minecraftforge/fluids/FluidStack;",
                    false);
            mv.visitFieldInsn(GETFIELD, "net/minecraftforge/fluids/FluidStack", "amount", "I");
            mv.visitJumpInsn(IFLE, l2);
            Label l50 = new Label();
            mv.visitLabel(l50);
            mv.visitLineNumber(118, l50);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "fuelTank",
                    "Lnet/minecraftforge/fluids/FluidTank;");
            mv.visitMethodInsn(
                    INVOKEVIRTUAL,
                    "net/minecraftforge/fluids/FluidTank",
                    "getFluid",
                    "()Lnet/minecraftforge/fluids/FluidStack;",
                    false);
            mv.visitVarInsn(ASTORE, 4);
            Label l51 = new Label();
            mv.visitLabel(l51);
            mv.visitLineNumber(119, l51);
            mv.visitVarInsn(ALOAD, 4);
            Label l52 = new Label();
            mv.visitJumpInsn(IFNONNULL, l52);
            Label l53 = new Label();
            mv.visitLabel(l53);
            mv.visitLineNumber(120, l53);
            mv.visitFieldInsn(
                    GETSTATIC,
                    "gtPlusPlus/core/item/chemistry/RocketFuels",
                    "mValidRocketFuels",
                    "Ljava/util/HashMap;");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/HashMap", "values", "()Ljava/util/Collection;", false);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Collection", "iterator", "()Ljava/util/Iterator;", true);
            mv.visitVarInsn(ASTORE, 6);
            Label l54 = new Label();
            mv.visitJumpInsn(GOTO, l54);
            Label l55 = new Label();
            mv.visitLabel(l55);
            mv.visitFrame(
                    F_FULL,
                    7,
                    new Object[] {
                        "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                        "net/minecraftforge/fluids/FluidStack",
                        TOP,
                        TOP,
                        "net/minecraftforge/fluids/FluidStack",
                        TOP,
                        "java/util/Iterator"
                    },
                    0,
                    new Object[] {});
            mv.visitVarInsn(ALOAD, 6);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
            mv.visitTypeInsn(CHECKCAST, "net/minecraftforge/fluids/Fluid");
            mv.visitVarInsn(ASTORE, 5);
            Label l56 = new Label();
            mv.visitLabel(l56);
            mv.visitLineNumber(121, l56);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "fuelTank",
                    "Lnet/minecraftforge/fluids/FluidTank;");
            mv.visitMethodInsn(
                    INVOKEVIRTUAL,
                    "net/minecraftforge/fluids/FluidTank",
                    "getFluid",
                    "()Lnet/minecraftforge/fluids/FluidStack;",
                    false);
            mv.visitMethodInsn(
                    INVOKEVIRTUAL,
                    "net/minecraftforge/fluids/FluidStack",
                    "getFluid",
                    "()Lnet/minecraftforge/fluids/Fluid;",
                    false);
            mv.visitVarInsn(ALOAD, 5);
            mv.visitJumpInsn(IF_ACMPNE, l54);
            Label l57 = new Label();
            mv.visitLabel(l57);
            mv.visitLineNumber(122, l57);
            mv.visitTypeInsn(NEW, "net/minecraftforge/fluids/FluidStack");
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 5);
            mv.visitInsn(ICONST_2);
            mv.visitMethodInsn(
                    INVOKESPECIAL,
                    "net/minecraftforge/fluids/FluidStack",
                    "<init>",
                    "(Lnet/minecraftforge/fluids/Fluid;I)V",
                    false);
            mv.visitVarInsn(ASTORE, 1);
            mv.visitLabel(l54);
            mv.visitLineNumber(120, l54);
            mv.visitFrame(F_SAME, 0, null, 0, null);
            mv.visitVarInsn(ALOAD, 6);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
            mv.visitJumpInsn(IFNE, l55);
            mv.visitLabel(l52);
            mv.visitLineNumber(126, l52);
            mv.visitFrame(
                    F_FULL,
                    5,
                    new Object[] {
                        "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                        "net/minecraftforge/fluids/FluidStack",
                        TOP,
                        TOP,
                        "net/minecraftforge/fluids/FluidStack"
                    },
                    0,
                    new Object[] {});
            mv.visitVarInsn(ALOAD, 4);
            mv.visitFieldInsn(GETFIELD, "net/minecraftforge/fluids/FluidStack", "amount", "I");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "fuelTank",
                    "Lnet/minecraftforge/fluids/FluidTank;");
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraftforge/fluids/FluidTank", "getCapacity", "()I", false);
            Label l58 = new Label();
            mv.visitJumpInsn(IF_ICMPGE, l58);
            Label l59 = new Label();
            mv.visitLabel(l59);
            mv.visitLineNumber(127, l59);
            mv.visitTypeInsn(NEW, "net/minecraftforge/fluids/FluidStack");
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 4);
            mv.visitInsn(ICONST_2);
            mv.visitMethodInsn(
                    INVOKESPECIAL,
                    "net/minecraftforge/fluids/FluidStack",
                    "<init>",
                    "(Lnet/minecraftforge/fluids/FluidStack;I)V",
                    false);
            mv.visitVarInsn(ASTORE, 1);
            mv.visitLabel(l58);
            mv.visitLineNumber(130, l58);
            mv.visitFrame(F_SAME, 0, null, 0, null);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitJumpInsn(IFNULL, l2);
            Label l60 = new Label();
            mv.visitLabel(l60);
            mv.visitLineNumber(131, l60);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "attachedFuelable",
                    "Lmicdoodle8/mods/galacticraft/api/entity/IFuelable;");
            mv.visitJumpInsn(IFNULL, l2);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "hasEnoughEnergyToRun",
                    "Z");
            mv.visitJumpInsn(IFEQ, l2);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader", "disabled", "Z");
            mv.visitJumpInsn(IFNE, l2);
            Label l61 = new Label();
            mv.visitLabel(l61);
            mv.visitLineNumber(132, l61);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "attachedFuelable",
                    "Lmicdoodle8/mods/galacticraft/api/entity/IFuelable;");
            mv.visitMethodInsn(
                    INVOKESTATIC,
                    "gtPlusPlus/xmod/galacticraft/util/GalacticUtils",
                    "getRocketTier",
                    "(Ljava/lang/Object;)I",
                    false);
            mv.visitVarInsn(ISTORE, 5);
            Label l62 = new Label();
            mv.visitLabel(l62);
            mv.visitLineNumber(133, l62);
            mv.visitVarInsn(ILOAD, 5);
            mv.visitJumpInsn(IFLE, l2);
            Label l63 = new Label();
            mv.visitLabel(l63);
            mv.visitLineNumber(134, l63);
            mv.visitVarInsn(ILOAD, 5);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(
                    INVOKESTATIC,
                    "gtPlusPlus/xmod/galacticraft/util/GalacticUtils",
                    "isFuelValidForTier",
                    "(ILnet/minecraftforge/fluids/FluidStack;)Z",
                    false);
            mv.visitJumpInsn(IFEQ, l2);
            Label l64 = new Label();
            mv.visitLabel(l64);
            mv.visitLineNumber(135, l64);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "attachedFuelable",
                    "Lmicdoodle8/mods/galacticraft/api/entity/IFuelable;");
            mv.visitVarInsn(ALOAD, 1);
            mv.visitInsn(ICONST_1);
            mv.visitMethodInsn(
                    INVOKEINTERFACE,
                    "micdoodle8/mods/galacticraft/api/entity/IFuelable",
                    "addFuel",
                    "(Lnet/minecraftforge/fluids/FluidStack;Z)I",
                    true);
            mv.visitVarInsn(ISTORE, 3);
            Label l65 = new Label();
            mv.visitLabel(l65);
            mv.visitLineNumber(136, l65);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ILOAD, 3);
            Label l66 = new Label();
            mv.visitJumpInsn(IFLE, l66);
            mv.visitInsn(ICONST_1);
            Label l67 = new Label();
            mv.visitJumpInsn(GOTO, l67);
            mv.visitLabel(l66);
            mv.visitFrame(
                    F_FULL,
                    6,
                    new Object[] {
                        "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                        "net/minecraftforge/fluids/FluidStack",
                        TOP,
                        INTEGER,
                        "net/minecraftforge/fluids/FluidStack",
                        INTEGER
                    },
                    1,
                    new Object[] {"micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader"});
            mv.visitInsn(ICONST_0);
            mv.visitLabel(l67);
            mv.visitFrame(
                    F_FULL,
                    6,
                    new Object[] {
                        "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                        "net/minecraftforge/fluids/FluidStack",
                        TOP,
                        INTEGER,
                        "net/minecraftforge/fluids/FluidStack",
                        INTEGER
                    },
                    2,
                    new Object[] {"micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader", INTEGER});
            mv.visitFieldInsn(
                    PUTFIELD, "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader", "loadedFuelLastTick", "Z");
            Label l68 = new Label();
            mv.visitLabel(l68);
            mv.visitLineNumber(137, l68);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                    GETFIELD,
                    "micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader",
                    "fuelTank",
                    "Lnet/minecraftforge/fluids/FluidTank;");
            mv.visitVarInsn(ILOAD, 3);
            mv.visitInsn(ICONST_1);
            mv.visitMethodInsn(
                    INVOKEVIRTUAL,
                    "net/minecraftforge/fluids/FluidTank",
                    "drain",
                    "(IZ)Lnet/minecraftforge/fluids/FluidStack;",
                    false);
            mv.visitInsn(POP);
            mv.visitLabel(l2);
            mv.visitLineNumber(144, l2);
            mv.visitFrame(
                    F_FULL,
                    1,
                    new Object[] {"micdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader"},
                    0,
                    new Object[] {});
            mv.visitInsn(RETURN);
            Label l69 = new Label();
            mv.visitLabel(l69);
            mv.visitLocalVariable(
                    "this", "Lmicdoodle8/mods/galacticraft/core/tile/TileEntityFuelLoader;", null, l0, l69, 0);
            mv.visitLocalVariable("liquid", "Lnet/minecraftforge/fluids/FluidStack;", null, l5, l2, 1);
            mv.visitLocalVariable("amount", "I", null, l37, l32, 2);
            mv.visitLocalVariable("filled", "I", null, l36, l32, 3);
            mv.visitLocalVariable("filled", "I", null, l65, l2, 3);
            mv.visitLocalVariable("isFuel", "Z", null, l10, l6, 4);
            mv.visitLocalVariable("liquidInTank", "Lnet/minecraftforge/fluids/FluidStack;", null, l13, l6, 5);
            mv.visitLocalVariable("didFill", "Z", null, l14, l6, 6);
            mv.visitLocalVariable("aFuelType", "Lnet/minecraftforge/fluids/Fluid;", null, l20, l18, 7);
            mv.visitLocalVariable("var8", "[Lnet/minecraftforge/common/util/ForgeDirection;", null, l35, l32, 4);
            mv.visitLocalVariable("dir", "Lnet/minecraftforge/common/util/ForgeDirection;", null, l40, l45, 5);
            mv.visitLocalVariable("pad", "L" + aTileEntity + ";", null, l41, l45, 6);
            mv.visitLocalVariable("mainTile", "L" + aTileEntity + ";", null, l44, l42, 7);
            mv.visitLocalVariable("liquidInTank", "Lnet/minecraftforge/fluids/FluidStack;", null, l51, l2, 4);
            mv.visitLocalVariable("aFuelType", "Lnet/minecraftforge/fluids/Fluid;", null, l56, l54, 5);
            mv.visitLocalVariable("aTier", "I", null, l62, l2, 5);
            mv.visitMaxs(5, 9);
            mv.visitEnd();
        }
    }

    public static final class localClassVisitor extends ClassVisitor {

        public localClassVisitor(ClassVisitor cv) {
            super(ASM5, cv);
            FMLRelaunchLog.log(
                    "[GT++ ASM] Galacticraft Fuel_Loader Patch", Level.INFO, "Inspecting Class " + className);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("updateEntity")) {
                FMLRelaunchLog.log("[GT++ ASM] Galacticraft Fuel_Loader Patch", Level.INFO, "Removing method " + name);
                return null;
            }

            MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
            return methodVisitor;
        }
    }
}
