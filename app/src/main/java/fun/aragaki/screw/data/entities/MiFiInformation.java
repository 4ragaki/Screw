package fun.aragaki.screw.data.entities;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MiFiInformation {

    public int mWanRxSpeed = 0;
    public int mWanTxSpeed = 0;
    public int mRoaming = 0;
    public int mPsLinkState = 0;
    public int mNetworkPlmn = 0;
    public String mNetworkNameStr;
    public int G = 0;
    public int mAuthFlag = 0;
    public int mAuthVersion = 0;
    public boolean mbRegStatus = false;
    public int mNetMode = 0;
    public int mSigLevel = 0;
    public int mBatteryLevel = 0;
    public int mBatteryValue = 0;
    public int mBatteryStatus = 0;
    public int mBatterySubStatus;
    public boolean mbAuth = false;
    public int mWanStatisticsMethod = 0;
    public long mWanStatisticsValue = 0;
    public long mWanStatisticsWaining;
    public long mWanStatisticsUpper;
    public String mStrStartDate;
    public String mStrEndDate;
    public boolean mbTFInsert = false;
    public long mTFFreeSize = 0;
    public int mSimStatus = 0;
    public int mPinStatus = 0;
    public int mActiveWlanClientNumber = 0;
    public int mChargerCurrent = 0;
    public int mOutputCurrent = 0;
    public int mCDetectStatus = 0;
    public int mChargeLeftTime = 0;
    public int mKickOffClientType = 255;
    public String mKickOffMacAddr;
    public int mNewSmsNumber = 0;

    public int check(MiFiInformation mifiInformation) {
        int i = mbRegStatus != mifiInformation.mbRegStatus ? 1 : 0;
        if (mNetMode != mifiInformation.mNetMode) i |= 2;
        if (mSigLevel != mifiInformation.mSigLevel) i |= 4;
        if (mBatteryLevel != mifiInformation.mBatteryLevel) i |= 8;
        if (mBatteryValue != mifiInformation.mBatteryValue) i |= 16;
        if (mBatteryStatus != mifiInformation.mBatteryStatus) i |= 32;
        if (mBatterySubStatus != mifiInformation.mBatterySubStatus) i |= 64;
//            i |= Rfc3492Idn.initial_n;
        if (mbTFInsert != mifiInformation.mbTFInsert) i |= 128;
//            i |= PublicSuffixListParser.MAX_LINE_LEN;
        if (mSimStatus != mifiInformation.mSimStatus) i |= 256;
//            i |= NTLMEngineImpl.FLAG_NEGOTIATE_NTLM;
        if (mPinStatus != mifiInformation.mPinStatus) i |= 512;
        if (mActiveWlanClientNumber != mifiInformation.mActiveWlanClientNumber) i |= 1024;
        if (mChargerCurrent != mifiInformation.mChargerCurrent) i |= 2048;
        if (mOutputCurrent != mifiInformation.mOutputCurrent) i |= 4096;
        if (mCDetectStatus != mifiInformation.mCDetectStatus) i |= 8192;
        if (mChargeLeftTime != mifiInformation.mChargeLeftTime) i |= 16384;
//            i |= NTLMEngineImpl.FLAG_NEGOTIATE_ALWAYS_SIGN;
        if (mRoaming != mifiInformation.mRoaming) i |= 32768;
        if (mPsLinkState != mifiInformation.mPsLinkState) i |= 65536;
        if (mNetworkPlmn != mifiInformation.mNetworkPlmn) i |= 131072;
        return i;
    }

    public void mifiInfo(byte[] bArr) {
        G = 0;
        mbRegStatus = bArr[0] > 0;
        mNetMode = bArr[1];
        mSigLevel = bArr[2];
        mBatteryLevel = bArr[3];
        mBatteryValue = bArr[4];
        mBatteryStatus = bArr[5];
        mBatterySubStatus = bArr[6];
        mbTFInsert = bArr[7] > 0;
        mSimStatus = bArr[8];
        mPinStatus = bArr[9];
        mActiveWlanClientNumber = bArr[10];
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        wrap.position(11);
        mChargerCurrent = wrap.getInt();
        mOutputCurrent = wrap.getInt();
        mCDetectStatus = wrap.get();
        mChargeLeftTime = wrap.getInt();
        if (bArr.length >= 25) {
            mRoaming = wrap.get();
            mPsLinkState = wrap.get();
            mNetworkPlmn = wrap.getInt();
            mbAuth = false;
            mWanStatisticsMethod = 0;
            mWanStatisticsValue = 0;
            mTFFreeSize = 0;
            mNewSmsNumber = 0;
            mWanRxSpeed = 0;
            mWanTxSpeed = 0;
            mKickOffClientType = 255;
            mAuthFlag = 0;
            mAuthVersion = 0;
        }
    }

    public void setAuthFlagVersion(byte[] bArr) {
        mbAuth = bArr[0] == 1;
        mAuthFlag = bArr[1];
        mAuthVersion = bArr[2];
    }

    public void setDeviceInformation(byte[] bArr) {
        mbRegStatus = bArr[0] > 0;
        mNetMode = bArr[1];
        mSigLevel = bArr[2];
        mBatteryLevel = bArr[3];
        mBatteryValue = bArr[4];
        mBatteryStatus = bArr[5];
        mBatterySubStatus = bArr[6];
        mbTFInsert = bArr[7] > 0;
        mSimStatus = bArr[8];
        mPinStatus = bArr[9];
        mActiveWlanClientNumber = bArr[10];
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        wrap.position(11);
        mChargerCurrent = wrap.getInt();
        mOutputCurrent = wrap.getInt();
        mCDetectStatus = wrap.get();
        mChargeLeftTime = wrap.getInt();
        if (bArr.length >= 25) {
            mRoaming = wrap.get();
            mPsLinkState = wrap.get();
            mNetworkPlmn = wrap.getInt();
        }
    }

    public void setKickOffClient(byte[] bArr) {
        byte[] bArr2 = new byte[17];
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        mKickOffClientType = wrap.get();
        int i2 = 0;
        while (i2 < 17) {
            int i3 = i2 + 1;
            bArr2[i2] = bArr[i3];
            i2 = i3;
        }
        mKickOffMacAddr = new String(bArr2);
    }

    public void setNewSmsNum(byte[] bArr) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        mNewSmsNumber = wrap.getInt();
    }

    public void setTFInsertFlag(byte[] bArr) {
        mbTFInsert = bArr[0] > 0;
    }

    public void setWanSpeed(byte[] bArr) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        if (bArr.length == 8) {
            mWanTxSpeed = wrap.getInt();
            mWanRxSpeed = wrap.getInt();
        }
    }

    public void setWanStatistics(byte[] bArr) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        mWanStatisticsMethod = wrap.get();
        mWanStatisticsValue = wrap.getLong();
    }

    public void setWanStatisticsPlan(byte[] bArr) {
        byte[] bArr2 = new byte[9];
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        mWanStatisticsMethod = wrap.get();
        mWanStatisticsWaining = wrap.getLong();
        mWanStatisticsUpper = wrap.getLong();
        System.arraycopy(bArr, 17, bArr2, 0, 9);
        mStrStartDate = new String(bArr2);
        System.arraycopy(bArr, 26, bArr2, 0, 9);
        mStrEndDate = new String(bArr2);
    }

    public void setNetworkName(byte[] bArr) {
        if (bArr != null && mAuthFlag > 1) {
            mNetworkNameStr = new String(bArr);
        }
    }

    public void setTFFreeSize(byte[] bArr) {
        if (mbTFInsert) {
            ByteBuffer wrap = ByteBuffer.wrap(bArr);
            wrap.order(ByteOrder.LITTLE_ENDIAN);
            mTFFreeSize = wrap.getLong();
        }
    }

    public void clearNetworkNameStr() {
        if (mAuthFlag > 1) mNetworkNameStr = "";
    }

    public String getNetworkName() {
        if (mAuthFlag > 1) return mNetworkNameStr;
        return null;
    }

    public String getKickOffMacAddr() {
        return mKickOffMacAddr;
    }

    public int getWanStatisticsMethod() {
        return mWanStatisticsMethod;
    }

    public long getWanStatisticsValue() {
        return mWanStatisticsValue;
    }

    public long getWanStatisticsWaining() {
        return mWanStatisticsWaining;
    }

    public int getWanTxSpeed() {
        return mWanTxSpeed;
    }

    public int getNewSmsNumber() {
        return mNewSmsNumber;
    }

    public String getStrStartDate() {
        return mStrStartDate;
    }

    public String getStrEndDate() {
        return mStrEndDate;
    }

    public boolean getTFInsert() {
        return mbTFInsert;
    }

    public boolean getAuthFlag() {
        return mbAuth;
    }

    public void setAuthFlag(byte[] bArr) {
        mbAuth = bArr[0] == 1;
    }

    public long getTFFreeSize() {
        return mTFFreeSize;
    }

    public int getKickOffClientType() {
        return mKickOffClientType;
    }

    public int getWanRxSpeed() {
        return mWanRxSpeed;
    }

    @NotNull
    @Override
    public String toString() {
        return "MifiInformation{mbRegStatus=" + mbRegStatus + ", mNetMode=" + mNetMode + ", mSigLevel=" + mSigLevel + ", mBatteryLevel=" + mBatteryLevel + ", mBatteryValue=" + mBatteryValue + ", mBatteryStatus=" + mBatteryStatus + ", mBatterySubStatus=" + mBatterySubStatus + ", mbAuth=" + mbAuth + ", mWanStatisticsMethod=" + mWanStatisticsMethod + ", mWanStatisticsValue=" + mWanStatisticsValue + ", mWanStatisticsWaining=" + mWanStatisticsWaining + ", mWanStatisticsUpper=" + mWanStatisticsUpper + ", mStrStartDate='" + mStrStartDate + '\'' + ", mStrEndDate='" + mStrEndDate + '\'' + ", mbTFInsert=" + mbTFInsert + ", mTFFreeSize=" + mTFFreeSize + ", mSimStatus=" + mSimStatus + ", mPinStatus=" + mPinStatus + ", mActiveWlanClientNumber=" + mActiveWlanClientNumber + ", mChargerCurrent=" + mChargerCurrent + ", mOutputCurrent=" + mOutputCurrent + ", mCDetectStatus=" + mCDetectStatus + ", mChargeLeftTime=" + mChargeLeftTime + ", mKickOffClientType=" + mKickOffClientType + ", mKickOffMacAddr='" + mKickOffMacAddr + '\'' + ", mNewSmsNumber=" + mNewSmsNumber + ", mWanRxSpeed=" + mWanRxSpeed + ", mWanTxSpeed=" + mWanTxSpeed + ", mRoaming=" + mRoaming + ", mPsLinkState=" + mPsLinkState + ", mNetworkPlmn=" + mNetworkPlmn + ", mNetworkNameStr=" + mNetworkNameStr + '}';
    }

    public MiFiDataTrafficInfo asTrafficInfo() {
        return new MiFiDataTrafficInfo(this);
    }

    public MiFiDataTrafficPlanInfo asTrafficPlanInfo() {
        return new MiFiDataTrafficPlanInfo(this);
    }

    public MiFiDeviceInfo asDeviceInfo() {
        return new MiFiDeviceInfo(this);
    }

    public MiFiKickoffClientInfo asKickoffClientInfo() {
        return new MiFiKickoffClientInfo(this);
    }

    public MiFiNetworkNameInfo asNetworkNameInfo() {
        return new MiFiNetworkNameInfo(this);
    }
}