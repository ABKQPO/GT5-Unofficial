package gregtech.api.util;

import java.util.function.Predicate;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import org.jetbrains.annotations.NotNull;

import gregtech.api.interfaces.fluid.IFluidStore;
import gregtech.api.metatileentity.implementations.MTEHatchOutput;

/**
 * Wrapper for output hatch to allow multiblocks to apply specific filter.
 */
public class OutputHatchWrapper implements IFluidStore {

    private final MTEHatchOutput outputHatch;
    private final Predicate<FluidStack> filter;

    public OutputHatchWrapper(MTEHatchOutput outputHatch, Predicate<FluidStack> filter) {
        this.outputHatch = outputHatch;
        this.filter = filter;
    }

    @Override
    public FluidStack getFluid() {
        return outputHatch.getFluid();
    }

    @Override
    public int getFluidAmount() {
        return outputHatch.getFluidAmount();
    }

    @Override
    public int getCapacity() {
        return outputHatch.getCapacity();
    }

    @Override
    public FluidTankInfo getInfo() {
        return outputHatch.getInfo();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return outputHatch.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return outputHatch.drain(maxDrain, doDrain);
    }

    @Override
    public boolean isEmptyAndAcceptsAnyFluid() {
        // https://github.com/GTNewHorizons/GT-New-Horizons-Modpack/issues/19177
        // always false because this hatch is filtered, and not always accepts any fluids
        // if not, when calculating parallels, it will be mistakenly used for other fluids,
        // but voiding them when they are actually put into this hatch.
        return false;
    }

    @Override
    public boolean canStoreFluid(@NotNull FluidStack fluidStack) {
        return outputHatch.canStoreFluid(fluidStack) && filter.test(fluidStack);
    }

    public MTEHatchOutput unwrap() {
        return outputHatch;
    }
}
