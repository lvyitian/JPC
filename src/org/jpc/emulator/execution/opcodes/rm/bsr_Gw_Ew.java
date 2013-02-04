package org.jpc.emulator.execution.opcodes.rm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class bsr_Gw_Ew extends Executable
{
    final int op1Index;
    final int op2Index;

    public bsr_Gw_Ew(int blockStart, Instruction parent)
    {
        super(blockStart, parent);
        op1Index = Processor.getRegIndex(parent.operand[0].toString());
        op2Index = Processor.getRegIndex(parent.operand[1].toString());
    }

    public Branch execute(Processor cpu)
    {
        Reg op1 = cpu.regs[op1Index];
        Reg op2 = cpu.regs[op2Index];
        if (op2.get16() == 0) {
	    cpu.zf(true);
	} else {
	    cpu.zf(false);
	    op1.set16(31-StaticOpcodes.numberOfLeadingZeros(0xFFFF&op2.get16()));
	}
        return Branch.None;
    }

    public boolean isBranch()
    {
        return false;
    }

    public String toString()
    {
        return this.getClass().getName();
    }
}