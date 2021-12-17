# ChangeLog

### Release_1.0.0_20211030_build_A

#### 功能构建

- 程序结构建立，mvn clean 测试通过。

- 实体及其维护服务建立，单元测试通过。
  - com.dwarfeng.capacitychecker.stack.bean.entity.AlarmSetting。
  - com.dwarfeng.capacitychecker.stack.bean.entity.CheckerInfo。
  - com.dwarfeng.capacitychecker.stack.bean.entity.CheckerSupport。
  - com.dwarfeng.capacitychecker.stack.bean.entity.CheckHistory。
  - com.dwarfeng.capacitychecker.stack.bean.entity.DriverInfo。
  - com.dwarfeng.capacitychecker.stack.bean.entity.DriverSupport。
  - com.dwarfeng.capacitychecker.stack.bean.entity.Section。
  - com.dwarfeng.capacitychecker.stack.bean.entity.AlarmInfo。

- 完成容量检查调度逻辑。

- 完成 node 模块，程序启动以及打包测试通过。

- 实现驱动器模块。
  - com.dwarfeng.capacitychecker.impl.handler.driver.CronDriverProvider。
  - com.dwarfeng.capacitychecker.impl.handler.driver.CronDriverSupporter。
  - com.dwarfeng.capacitychecker.impl.handler.driver.FixedDelayDriverProvider。
  - com.dwarfeng.capacitychecker.impl.handler.driver.FixedDelayDriverSupporter。
  - com.dwarfeng.capacitychecker.impl.handler.driver.FixedRateDriverProvider。
  - com.dwarfeng.capacitychecker.impl.handler.driver.FixedRateDriverSupporter。

#### Bug修复

- (无)

#### 功能移除

- (无)
