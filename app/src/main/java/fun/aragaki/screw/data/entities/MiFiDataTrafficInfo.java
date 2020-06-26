package fun.aragaki.screw.data.entities;


import org.jetbrains.annotations.NotNull;

public class MiFiDataTrafficInfo {
    public int mWanStatisticsMethod;
    public long mWanStatisticsValue;

    public MiFiDataTrafficInfo(MiFiInformation mifiInformation) {
        this.mWanStatisticsMethod = mifiInformation.mWanStatisticsMethod;
        this.mWanStatisticsValue = mifiInformation.mWanStatisticsValue;
    }

    @NotNull
    @Override
    public String toString() {
        return "MiFiDataTrafficInfo{" +
                "mWanStatisticsMethod=" + mWanStatisticsMethod +
                ", mWanStatisticsValue=" + mWanStatisticsValue +
                '}';
    }
}