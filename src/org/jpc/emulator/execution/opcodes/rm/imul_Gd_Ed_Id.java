package org.jpc.emulator.execution.opcodes.rm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class imul_Gd_Ed_Id extends Executable
{
    final int op1Index;
    final int op2Index;
    final int immd;

    public imul_Gd_Ed_Id(int blockStart, Instruction parent)
    {
        super(blockStart, parent);
        op1Index = Processor.getRegIndex(parent.operand[0].toString());
        op2Index = Processor.getRegIndex(parent.operand[1].toString());
        immd = (int)parent.operand[2].lval;
    }

    public Branch execute(Processor cpu)
    {
        Reg op1 = cpu.regs[op1Index];
        Reg op2 = cpu.regs[op2Index];
            cpu.flagStatus = OSZAPC;
            cpu.flagOp1 = immd;
            cpu.flagOp2 = op2.get32();
            long res64 = (((long) cpu.flagOp1)*cpu.flagOp2);
            cpu.flagResult = (int) res64;
            op1.set32(cpu.flagResult);
            cpu.flagIns = UCodes.IMUL32;
            if (res64 < 0)
                cpu.sf = true;
            else
                cpu.sf = false;
            cpu.flagStatus &= ~SF;
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