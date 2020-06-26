package fun.aragaki.screw.data.entities;

import org.jetbrains.annotations.NotNull;

public class MiFiDataTrafficPlanInfo {
    public int mWanStatisticsMethod;
    public long mWanStatisticsWaining;
    public long mWanStatisticsUpper;
    public String mStrStartDate;
    public String mStrEndDate;

    public MiFiDataTrafficPlanInfo(MiFiInformation mifiInfomation) {
        this.mWanStatisticsMethod = mifiInfomation.mWanStatisticsMethod;
        this.mWanStatisticsWaining = mifiInfomation.mWanStatisticsWaining;
        this.mWanStatisticsUpper = mifiInfomation.mWanStatisticsUpper;
        this.mStrStartDate = mifiInfomation.mStrStartDate;
        this.mStrEndDate = mifiInfomation.mStrEndDate;
    }

    @NotNull
    @Override
    public String toString() {
        return "MiFiDataTrafficPlanInfo{" +
                "mWanStatisticsMethod=" + mWanStatisticsMethod +
                ", mWanStatisticsWaining=" + mWanStatisticsWaining +
                ", mWanStatisticsUpper=" + mWanStatisticsUpper +
                ", mStrStartDate='" + mStrStartDate + '\'' +
                ", mStrEndDate='" + mStrEndDate + '\'' +
                '}';
    }
}