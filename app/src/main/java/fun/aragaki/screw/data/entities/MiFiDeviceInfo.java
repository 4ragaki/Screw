package fun.aragaki.screw.data.entities;

import org.jetbrains.annotations.NotNull;

public class MiFiDeviceInfo {

    public boolean mbRegStatus;
    public int mNetMode;
    public int mSigLevel;
    public int mBatteryLevel;
    public int mBatteryValue;
    public int mBatteryStatus;
    public int mBatterySubStatus;
    public boolean mbTFInsert;
    public int mSimStatus;
    public int mPinStatus;
    public int mActiveWlanClientNumber;
    public int mChargerCurrent;
    public int mOutputCurrent;
    public int mCDetectStatus;
    public int mChargeLeftTime;
    public int mRoaming;
    public int mPsLinkState;
    public int mNetworkPlmn;

    public MiFiDeviceInfo(MiFiInformation mifiInfomation) {
        this.mbRegStatus = mifiInfomation.mbRegStatus;
        this.mNetMode = mifiInfomation.mNetMode;
        this.mSigLevel = mifiInfomation.mSigLevel;
        this.mBatteryLevel = mifiInfomation.mBatteryLevel;
        this.mBatteryValue = mifiInfomation.mBatteryValue;
        this.mBatteryStatus = mifiInfomation.mBatteryStatus;
        this.mBatterySubStatus = mifiInfomation.mBatterySubStatus;
        this.mbTFInsert = mifiInfomation.mbTFInsert;
        this.mSimStatus = mifiInfomation.mSimStatus;
        this.mPinStatus = mifiInfomation.mPinStatus;
        this.mActiveWlanClientNumber = mifiInfomation.mActiveWlanClientNumber;
        this.mChargerCurrent = mifiInfomation.mChargerCurrent;
        this.mOutputCurrent = mifiInfomation.mOutputCurrent;
        this.mCDetectStatus = mifiInfomation.mCDetectStatus;
        this.mChargeLeftTime = mifiInfomation.mChargeLeftTime;
        this.mRoaming = mifiInfomation.mRoaming;
        this.mPsLinkState = mifiInfomation.mPsLinkState;
        this.mNetworkPlmn = mifiInfomation.mNetworkPlmn;
    }

    @NotNull
    @Override
    public String toString() {
        return "MiFiDeviceInfo{" +
                "mbRegStatus=" + mbRegStatus +
                ", mNetMode=" + mNetMode +
                ", mSigLevel=" + mSigLevel +
                ", mBatteryLevel=" + mBatteryLevel +
                ", mBatteryValue=" + mBatteryValue +
                ", mBatteryStatus=" + mBatteryStatus +
                ", mBatterySubStatus=" + mBatterySubStatus +
                ", mbTFInsert=" + mbTFInsert +
                ", mSimStatus=" + mSimStatus +
                ", mPinStatus=" + mPinStatus +
                ", mActiveWlanClientNumber=" + mActiveWlanClientNumber +
                ", mChargerCurrent=" + mChargerCurrent +
                ", mOutputCurrent=" + mOutputCurrent +
                ", mCDetectStatus=" + mCDetectStatus +
                ", mChargeLeftTime=" + mChargeLeftTime +
                ", mRoaming=" + mRoaming +
                ", mPsLinkState=" + mPsLinkState +
                ", mNetworkPlmn=" + mNetworkPlmn +
                '}';
    }
}