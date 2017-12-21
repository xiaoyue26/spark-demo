package com.xiaoyue26.www.hbase.data;


import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-01-15")
public class UserQuestionStat implements org.apache.thrift.TBase<UserQuestionStat, UserQuestionStat._Fields>, java.io.Serializable, Cloneable, Comparable<UserQuestionStat> {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("UserQuestionStat");

    private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("userId", org.apache.thrift.protocol.TType.I32, (short)1);
    private static final org.apache.thrift.protocol.TField QUESTION_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("questionId", org.apache.thrift.protocol.TType.I32, (short)2);
    private static final org.apache.thrift.protocol.TField ANSWER_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("answerCount", org.apache.thrift.protocol.TType.I32, (short)3);
    private static final org.apache.thrift.protocol.TField CORRECT_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("correctCount", org.apache.thrift.protocol.TType.I32, (short)4);
    private static final org.apache.thrift.protocol.TField LAST_ANSWER_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("lastAnswerTime", org.apache.thrift.protocol.TType.I64, (short)5);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
        schemes.put(StandardScheme.class, new UserQuestionStatStandardSchemeFactory());
        schemes.put(TupleScheme.class, new UserQuestionStatTupleSchemeFactory());
    }

    public int userId; // optional
    public int questionId; // optional
    public int answerCount; // optional
    public int correctCount; // optional
    public long lastAnswerTime; // optional

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
        USER_ID((short)1, "userId"),
        QUESTION_ID((short)2, "questionId"),
        ANSWER_COUNT((short)3, "answerCount"),
        CORRECT_COUNT((short)4, "correctCount"),
        LAST_ANSWER_TIME((short)5, "lastAnswerTime");

        private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

        static {
            for (_Fields field : EnumSet.allOf(_Fields.class)) {
                byName.put(field.getFieldName(), field);
            }
        }

        /**
         * Find the _Fields constant that matches fieldId, or null if its not found.
         */
        public static _Fields findByThriftId(int fieldId) {
            switch(fieldId) {
                case 1: // USER_ID
                    return USER_ID;
                case 2: // QUESTION_ID
                    return QUESTION_ID;
                case 3: // ANSWER_COUNT
                    return ANSWER_COUNT;
                case 4: // CORRECT_COUNT
                    return CORRECT_COUNT;
                case 5: // LAST_ANSWER_TIME
                    return LAST_ANSWER_TIME;
                default:
                    return null;
            }
        }

        /**
         * Find the _Fields constant that matches fieldId, throwing an exception
         * if it is not found.
         */
        public static _Fields findByThriftIdOrThrow(int fieldId) {
            _Fields fields = findByThriftId(fieldId);
            if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
            return fields;
        }

        /**
         * Find the _Fields constant that matches name, or null if its not found.
         */
        public static _Fields findByName(String name) {
            return byName.get(name);
        }

        private final short _thriftId;
        private final String _fieldName;

        _Fields(short thriftId, String fieldName) {
            _thriftId = thriftId;
            _fieldName = fieldName;
        }

        public short getThriftFieldId() {
            return _thriftId;
        }

        public String getFieldName() {
            return _fieldName;
        }
    }

    // isset id assignments
    private static final int __USERID_ISSET_ID = 0;
    private static final int __QUESTIONID_ISSET_ID = 1;
    private static final int __ANSWERCOUNT_ISSET_ID = 2;
    private static final int __CORRECTCOUNT_ISSET_ID = 3;
    private static final int __LASTANSWERTIME_ISSET_ID = 4;
    private byte __isset_bitfield = 0;
    private static final _Fields optionals[] = {_Fields.USER_ID,_Fields.QUESTION_ID,_Fields.ANSWER_COUNT,_Fields.CORRECT_COUNT,_Fields.LAST_ANSWER_TIME};
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
        Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
        tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("userId", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
        tmpMap.put(_Fields.QUESTION_ID, new org.apache.thrift.meta_data.FieldMetaData("questionId", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
        tmpMap.put(_Fields.ANSWER_COUNT, new org.apache.thrift.meta_data.FieldMetaData("answerCount", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
        tmpMap.put(_Fields.CORRECT_COUNT, new org.apache.thrift.meta_data.FieldMetaData("correctCount", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
        tmpMap.put(_Fields.LAST_ANSWER_TIME, new org.apache.thrift.meta_data.FieldMetaData("lastAnswerTime", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
        metaDataMap = Collections.unmodifiableMap(tmpMap);
        org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(UserQuestionStat.class, metaDataMap);
    }

    public UserQuestionStat() {
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public UserQuestionStat(UserQuestionStat other) {
        __isset_bitfield = other.__isset_bitfield;
        this.userId = other.userId;
        this.questionId = other.questionId;
        this.answerCount = other.answerCount;
        this.correctCount = other.correctCount;
        this.lastAnswerTime = other.lastAnswerTime;
    }

    public UserQuestionStat deepCopy() {
        return new UserQuestionStat(this);
    }

    @Override
    public void clear() {
        setUserIdIsSet(false);
        this.userId = 0;
        setQuestionIdIsSet(false);
        this.questionId = 0;
        setAnswerCountIsSet(false);
        this.answerCount = 0;
        setCorrectCountIsSet(false);
        this.correctCount = 0;
        setLastAnswerTimeIsSet(false);
        this.lastAnswerTime = 0;
    }

    public int getUserId() {
        return this.userId;
    }

    public UserQuestionStat setUserId(int userId) {
        this.userId = userId;
        setUserIdIsSet(true);
        return this;
    }

    public void unsetUserId() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __USERID_ISSET_ID);
    }

    /** Returns true if field userId is set (has been assigned a value) and false otherwise */
    public boolean isSetUserId() {
        return EncodingUtils.testBit(__isset_bitfield, __USERID_ISSET_ID);
    }

    public void setUserIdIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __USERID_ISSET_ID, value);
    }

    public int getQuestionId() {
        return this.questionId;
    }

    public UserQuestionStat setQuestionId(int questionId) {
        this.questionId = questionId;
        setQuestionIdIsSet(true);
        return this;
    }

    public void unsetQuestionId() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __QUESTIONID_ISSET_ID);
    }

    /** Returns true if field questionId is set (has been assigned a value) and false otherwise */
    public boolean isSetQuestionId() {
        return EncodingUtils.testBit(__isset_bitfield, __QUESTIONID_ISSET_ID);
    }

    public void setQuestionIdIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __QUESTIONID_ISSET_ID, value);
    }

    public int getAnswerCount() {
        return this.answerCount;
    }

    public UserQuestionStat setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
        setAnswerCountIsSet(true);
        return this;
    }

    public void unsetAnswerCount() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ANSWERCOUNT_ISSET_ID);
    }

    /** Returns true if field answerCount is set (has been assigned a value) and false otherwise */
    public boolean isSetAnswerCount() {
        return EncodingUtils.testBit(__isset_bitfield, __ANSWERCOUNT_ISSET_ID);
    }

    public void setAnswerCountIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ANSWERCOUNT_ISSET_ID, value);
    }

    public int getCorrectCount() {
        return this.correctCount;
    }

    public UserQuestionStat setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
        setCorrectCountIsSet(true);
        return this;
    }

    public void unsetCorrectCount() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CORRECTCOUNT_ISSET_ID);
    }

    /** Returns true if field correctCount is set (has been assigned a value) and false otherwise */
    public boolean isSetCorrectCount() {
        return EncodingUtils.testBit(__isset_bitfield, __CORRECTCOUNT_ISSET_ID);
    }

    public void setCorrectCountIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CORRECTCOUNT_ISSET_ID, value);
    }

    public long getLastAnswerTime() {
        return this.lastAnswerTime;
    }

    public UserQuestionStat setLastAnswerTime(long lastAnswerTime) {
        this.lastAnswerTime = lastAnswerTime;
        setLastAnswerTimeIsSet(true);
        return this;
    }

    public void unsetLastAnswerTime() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LASTANSWERTIME_ISSET_ID);
    }

    /** Returns true if field lastAnswerTime is set (has been assigned a value) and false otherwise */
    public boolean isSetLastAnswerTime() {
        return EncodingUtils.testBit(__isset_bitfield, __LASTANSWERTIME_ISSET_ID);
    }

    public void setLastAnswerTimeIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LASTANSWERTIME_ISSET_ID, value);
    }

    public void setFieldValue(_Fields field, Object value) {
        switch (field) {
            case USER_ID:
                if (value == null) {
                    unsetUserId();
                } else {
                    setUserId((Integer)value);
                }
                break;

            case QUESTION_ID:
                if (value == null) {
                    unsetQuestionId();
                } else {
                    setQuestionId((Integer)value);
                }
                break;

            case ANSWER_COUNT:
                if (value == null) {
                    unsetAnswerCount();
                } else {
                    setAnswerCount((Integer)value);
                }
                break;

            case CORRECT_COUNT:
                if (value == null) {
                    unsetCorrectCount();
                } else {
                    setCorrectCount((Integer)value);
                }
                break;

            case LAST_ANSWER_TIME:
                if (value == null) {
                    unsetLastAnswerTime();
                } else {
                    setLastAnswerTime((Long)value);
                }
                break;

        }
    }

    public Object getFieldValue(_Fields field) {
        switch (field) {
            case USER_ID:
                return getUserId();

            case QUESTION_ID:
                return getQuestionId();

            case ANSWER_COUNT:
                return getAnswerCount();

            case CORRECT_COUNT:
                return getCorrectCount();

            case LAST_ANSWER_TIME:
                return getLastAnswerTime();

        }
        throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
        if (field == null) {
            throw new IllegalArgumentException();
        }

        switch (field) {
            case USER_ID:
                return isSetUserId();
            case QUESTION_ID:
                return isSetQuestionId();
            case ANSWER_COUNT:
                return isSetAnswerCount();
            case CORRECT_COUNT:
                return isSetCorrectCount();
            case LAST_ANSWER_TIME:
                return isSetLastAnswerTime();
        }
        throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
        if (that == null)
            return false;
        if (that instanceof UserQuestionStat)
            return this.equals((UserQuestionStat)that);
        return false;
    }

    public boolean equals(UserQuestionStat that) {
        if (that == null)
            return false;

        boolean this_present_userId = true && this.isSetUserId();
        boolean that_present_userId = true && that.isSetUserId();
        if (this_present_userId || that_present_userId) {
            if (!(this_present_userId && that_present_userId))
                return false;
            if (this.userId != that.userId)
                return false;
        }

        boolean this_present_questionId = true && this.isSetQuestionId();
        boolean that_present_questionId = true && that.isSetQuestionId();
        if (this_present_questionId || that_present_questionId) {
            if (!(this_present_questionId && that_present_questionId))
                return false;
            if (this.questionId != that.questionId)
                return false;
        }

        boolean this_present_answerCount = true && this.isSetAnswerCount();
        boolean that_present_answerCount = true && that.isSetAnswerCount();
        if (this_present_answerCount || that_present_answerCount) {
            if (!(this_present_answerCount && that_present_answerCount))
                return false;
            if (this.answerCount != that.answerCount)
                return false;
        }

        boolean this_present_correctCount = true && this.isSetCorrectCount();
        boolean that_present_correctCount = true && that.isSetCorrectCount();
        if (this_present_correctCount || that_present_correctCount) {
            if (!(this_present_correctCount && that_present_correctCount))
                return false;
            if (this.correctCount != that.correctCount)
                return false;
        }

        boolean this_present_lastAnswerTime = true && this.isSetLastAnswerTime();
        boolean that_present_lastAnswerTime = true && that.isSetLastAnswerTime();
        if (this_present_lastAnswerTime || that_present_lastAnswerTime) {
            if (!(this_present_lastAnswerTime && that_present_lastAnswerTime))
                return false;
            if (this.lastAnswerTime != that.lastAnswerTime)
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        List<Object> list = new ArrayList<Object>();

        boolean present_userId = true && (isSetUserId());
        list.add(present_userId);
        if (present_userId)
            list.add(userId);

        boolean present_questionId = true && (isSetQuestionId());
        list.add(present_questionId);
        if (present_questionId)
            list.add(questionId);

        boolean present_answerCount = true && (isSetAnswerCount());
        list.add(present_answerCount);
        if (present_answerCount)
            list.add(answerCount);

        boolean present_correctCount = true && (isSetCorrectCount());
        list.add(present_correctCount);
        if (present_correctCount)
            list.add(correctCount);

        boolean present_lastAnswerTime = true && (isSetLastAnswerTime());
        list.add(present_lastAnswerTime);
        if (present_lastAnswerTime)
            list.add(lastAnswerTime);

        return list.hashCode();
    }

    @Override
    public int compareTo(UserQuestionStat other) {
        if (!getClass().equals(other.getClass())) {
            return getClass().getName().compareTo(other.getClass().getName());
        }

        int lastComparison = 0;

        lastComparison = Boolean.valueOf(isSetUserId()).compareTo(other.isSetUserId());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetUserId()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userId, other.userId);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetQuestionId()).compareTo(other.isSetQuestionId());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetQuestionId()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.questionId, other.questionId);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetAnswerCount()).compareTo(other.isSetAnswerCount());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetAnswerCount()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.answerCount, other.answerCount);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetCorrectCount()).compareTo(other.isSetCorrectCount());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetCorrectCount()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.correctCount, other.correctCount);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetLastAnswerTime()).compareTo(other.isSetLastAnswerTime());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetLastAnswerTime()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lastAnswerTime, other.lastAnswerTime);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        return 0;
    }

    public _Fields fieldForId(int fieldId) {
        return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
        schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
        schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserQuestionStat(");
        boolean first = true;

        if (isSetUserId()) {
            sb.append("userId:");
            sb.append(this.userId);
            first = false;
        }
        if (isSetQuestionId()) {
            if (!first) sb.append(", ");
            sb.append("questionId:");
            sb.append(this.questionId);
            first = false;
        }
        if (isSetAnswerCount()) {
            if (!first) sb.append(", ");
            sb.append("answerCount:");
            sb.append(this.answerCount);
            first = false;
        }
        if (isSetCorrectCount()) {
            if (!first) sb.append(", ");
            sb.append("correctCount:");
            sb.append(this.correctCount);
            first = false;
        }
        if (isSetLastAnswerTime()) {
            if (!first) sb.append(", ");
            sb.append("lastAnswerTime:");
            sb.append(this.lastAnswerTime);
            first = false;
        }
        sb.append(")");
        return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
        // check for required fields
        // check for sub-struct validity
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
        try {
            write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
        } catch (org.apache.thrift.TException te) {
            throw new java.io.IOException(te);
        }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        try {
            // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
            __isset_bitfield = 0;
            read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
        } catch (org.apache.thrift.TException te) {
            throw new java.io.IOException(te);
        }
    }

    private static class UserQuestionStatStandardSchemeFactory implements SchemeFactory {
        public UserQuestionStatStandardScheme getScheme() {
            return new UserQuestionStatStandardScheme();
        }
    }

    private static class UserQuestionStatStandardScheme extends StandardScheme<UserQuestionStat> {

        public void read(org.apache.thrift.protocol.TProtocol iprot, UserQuestionStat struct) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField schemeField;
            iprot.readStructBegin();
            while (true)
            {
                schemeField = iprot.readFieldBegin();
                if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (schemeField.id) {
                    case 1: // USER_ID
                        if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                            struct.userId = iprot.readI32();
                            struct.setUserIdIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 2: // QUESTION_ID
                        if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                            struct.questionId = iprot.readI32();
                            struct.setQuestionIdIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 3: // ANSWER_COUNT
                        if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                            struct.answerCount = iprot.readI32();
                            struct.setAnswerCountIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 4: // CORRECT_COUNT
                        if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                            struct.correctCount = iprot.readI32();
                            struct.setCorrectCountIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 5: // LAST_ANSWER_TIME
                        if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
                            struct.lastAnswerTime = iprot.readI64();
                            struct.setLastAnswerTimeIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    default:
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

            // check for required fields of primitive type, which can't be checked in the validate method
            struct.validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot, UserQuestionStat struct) throws org.apache.thrift.TException {
            struct.validate();

            oprot.writeStructBegin(STRUCT_DESC);
            if (struct.isSetUserId()) {
                oprot.writeFieldBegin(USER_ID_FIELD_DESC);
                oprot.writeI32(struct.userId);
                oprot.writeFieldEnd();
            }
            if (struct.isSetQuestionId()) {
                oprot.writeFieldBegin(QUESTION_ID_FIELD_DESC);
                oprot.writeI32(struct.questionId);
                oprot.writeFieldEnd();
            }
            if (struct.isSetAnswerCount()) {
                oprot.writeFieldBegin(ANSWER_COUNT_FIELD_DESC);
                oprot.writeI32(struct.answerCount);
                oprot.writeFieldEnd();
            }
            if (struct.isSetCorrectCount()) {
                oprot.writeFieldBegin(CORRECT_COUNT_FIELD_DESC);
                oprot.writeI32(struct.correctCount);
                oprot.writeFieldEnd();
            }
            if (struct.isSetLastAnswerTime()) {
                oprot.writeFieldBegin(LAST_ANSWER_TIME_FIELD_DESC);
                oprot.writeI64(struct.lastAnswerTime);
                oprot.writeFieldEnd();
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

    }

    private static class UserQuestionStatTupleSchemeFactory implements SchemeFactory {
        public UserQuestionStatTupleScheme getScheme() {
            return new UserQuestionStatTupleScheme();
        }
    }

    private static class UserQuestionStatTupleScheme extends TupleScheme<UserQuestionStat> {

        @Override
        public void write(org.apache.thrift.protocol.TProtocol prot, UserQuestionStat struct) throws org.apache.thrift.TException {
            TTupleProtocol oprot = (TTupleProtocol) prot;
            BitSet optionals = new BitSet();
            if (struct.isSetUserId()) {
                optionals.set(0);
            }
            if (struct.isSetQuestionId()) {
                optionals.set(1);
            }
            if (struct.isSetAnswerCount()) {
                optionals.set(2);
            }
            if (struct.isSetCorrectCount()) {
                optionals.set(3);
            }
            if (struct.isSetLastAnswerTime()) {
                optionals.set(4);
            }
            oprot.writeBitSet(optionals, 5);
            if (struct.isSetUserId()) {
                oprot.writeI32(struct.userId);
            }
            if (struct.isSetQuestionId()) {
                oprot.writeI32(struct.questionId);
            }
            if (struct.isSetAnswerCount()) {
                oprot.writeI32(struct.answerCount);
            }
            if (struct.isSetCorrectCount()) {
                oprot.writeI32(struct.correctCount);
            }
            if (struct.isSetLastAnswerTime()) {
                oprot.writeI64(struct.lastAnswerTime);
            }
        }

        @Override
        public void read(org.apache.thrift.protocol.TProtocol prot, UserQuestionStat struct) throws org.apache.thrift.TException {
            TTupleProtocol iprot = (TTupleProtocol) prot;
            BitSet incoming = iprot.readBitSet(5);
            if (incoming.get(0)) {
                struct.userId = iprot.readI32();
                struct.setUserIdIsSet(true);
            }
            if (incoming.get(1)) {
                struct.questionId = iprot.readI32();
                struct.setQuestionIdIsSet(true);
            }
            if (incoming.get(2)) {
                struct.answerCount = iprot.readI32();
                struct.setAnswerCountIsSet(true);
            }
            if (incoming.get(3)) {
                struct.correctCount = iprot.readI32();
                struct.setCorrectCountIsSet(true);
            }
            if (incoming.get(4)) {
                struct.lastAnswerTime = iprot.readI64();
                struct.setLastAnswerTimeIsSet(true);
            }
        }
    }

}

