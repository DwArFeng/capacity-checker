import com.dwarfeng.capacitychecker.impl.handler.checker.GroovyCheckerRegistry
import com.dwarfeng.capacitychecker.stack.bean.dto.CheckResult
import com.dwarfeng.capacitychecker.stack.exception.CheckerException
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey

/**
 * 示例脚本，无实际意义。
 * <p> 取当前的时间戳作为容量并返回。
 */
@SuppressWarnings("GrPackage")
class ExampleCheckerProcessor implements GroovyCheckerRegistry.Processor {

    @Override
    CheckResult judge(LongIdKey checkerInfoKey) throws CheckerException {
        return new CheckResult(System.currentTimeMillis())
    }
}
