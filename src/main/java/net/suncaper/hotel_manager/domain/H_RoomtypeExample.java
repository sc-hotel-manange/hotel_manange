package net.suncaper.hotel_manager.domain;

import java.util.ArrayList;
import java.util.List;

public class H_RoomtypeExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table h_roomtype
     *
     * @mbg.generated Tue Jul 30 09:29:41 CST 2019
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table h_roomtype
     *
     * @mbg.generated Tue Jul 30 09:29:41 CST 2019
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table h_roomtype
     *
     * @mbg.generated Tue Jul 30 09:29:41 CST 2019
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Tue Jul 30 09:29:41 CST 2019
     */
    public H_RoomtypeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Tue Jul 30 09:29:41 CST 2019
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Tue Jul 30 09:29:41 CST 2019
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Tue Jul 30 09:29:41 CST 2019
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Tue Jul 30 09:29:41 CST 2019
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Tue Jul 30 09:29:41 CST 2019
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Tue Jul 30 09:29:41 CST 2019
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Tue Jul 30 09:29:41 CST 2019
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Tue Jul 30 09:29:41 CST 2019
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Tue Jul 30 09:29:41 CST 2019
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Tue Jul 30 09:29:41 CST 2019
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table h_roomtype
     *
     * @mbg.generated Tue Jul 30 09:29:41 CST 2019
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andRtIdIsNull() {
            addCriterion("rt_id is null");
            return (Criteria) this;
        }

        public Criteria andRtIdIsNotNull() {
            addCriterion("rt_id is not null");
            return (Criteria) this;
        }

        public Criteria andRtIdEqualTo(Integer value) {
            addCriterion("rt_id =", value, "rtId");
            return (Criteria) this;
        }

        public Criteria andRtIdNotEqualTo(Integer value) {
            addCriterion("rt_id <>", value, "rtId");
            return (Criteria) this;
        }

        public Criteria andRtIdGreaterThan(Integer value) {
            addCriterion("rt_id >", value, "rtId");
            return (Criteria) this;
        }

        public Criteria andRtIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("rt_id >=", value, "rtId");
            return (Criteria) this;
        }

        public Criteria andRtIdLessThan(Integer value) {
            addCriterion("rt_id <", value, "rtId");
            return (Criteria) this;
        }

        public Criteria andRtIdLessThanOrEqualTo(Integer value) {
            addCriterion("rt_id <=", value, "rtId");
            return (Criteria) this;
        }

        public Criteria andRtIdIn(List<Integer> values) {
            addCriterion("rt_id in", values, "rtId");
            return (Criteria) this;
        }

        public Criteria andRtIdNotIn(List<Integer> values) {
            addCriterion("rt_id not in", values, "rtId");
            return (Criteria) this;
        }

        public Criteria andRtIdBetween(Integer value1, Integer value2) {
            addCriterion("rt_id between", value1, value2, "rtId");
            return (Criteria) this;
        }

        public Criteria andRtIdNotBetween(Integer value1, Integer value2) {
            addCriterion("rt_id not between", value1, value2, "rtId");
            return (Criteria) this;
        }

        public Criteria andHotelIdIsNull() {
            addCriterion("hotel_id is null");
            return (Criteria) this;
        }

        public Criteria andHotelIdIsNotNull() {
            addCriterion("hotel_id is not null");
            return (Criteria) this;
        }

        public Criteria andHotelIdEqualTo(Integer value) {
            addCriterion("hotel_id =", value, "hotelId");
            return (Criteria) this;
        }

        public Criteria andHotelIdNotEqualTo(Integer value) {
            addCriterion("hotel_id <>", value, "hotelId");
            return (Criteria) this;
        }

        public Criteria andHotelIdGreaterThan(Integer value) {
            addCriterion("hotel_id >", value, "hotelId");
            return (Criteria) this;
        }

        public Criteria andHotelIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("hotel_id >=", value, "hotelId");
            return (Criteria) this;
        }

        public Criteria andHotelIdLessThan(Integer value) {
            addCriterion("hotel_id <", value, "hotelId");
            return (Criteria) this;
        }

        public Criteria andHotelIdLessThanOrEqualTo(Integer value) {
            addCriterion("hotel_id <=", value, "hotelId");
            return (Criteria) this;
        }

        public Criteria andHotelIdIn(List<Integer> values) {
            addCriterion("hotel_id in", values, "hotelId");
            return (Criteria) this;
        }

        public Criteria andHotelIdNotIn(List<Integer> values) {
            addCriterion("hotel_id not in", values, "hotelId");
            return (Criteria) this;
        }

        public Criteria andHotelIdBetween(Integer value1, Integer value2) {
            addCriterion("hotel_id between", value1, value2, "hotelId");
            return (Criteria) this;
        }

        public Criteria andHotelIdNotBetween(Integer value1, Integer value2) {
            addCriterion("hotel_id not between", value1, value2, "hotelId");
            return (Criteria) this;
        }

        public Criteria andRtTypeIsNull() {
            addCriterion("rt_type is null");
            return (Criteria) this;
        }

        public Criteria andRtTypeIsNotNull() {
            addCriterion("rt_type is not null");
            return (Criteria) this;
        }

        public Criteria andRtTypeEqualTo(String value) {
            addCriterion("rt_type =", value, "rtType");
            return (Criteria) this;
        }

        public Criteria andRtTypeNotEqualTo(String value) {
            addCriterion("rt_type <>", value, "rtType");
            return (Criteria) this;
        }

        public Criteria andRtTypeGreaterThan(String value) {
            addCriterion("rt_type >", value, "rtType");
            return (Criteria) this;
        }

        public Criteria andRtTypeGreaterThanOrEqualTo(String value) {
            addCriterion("rt_type >=", value, "rtType");
            return (Criteria) this;
        }

        public Criteria andRtTypeLessThan(String value) {
            addCriterion("rt_type <", value, "rtType");
            return (Criteria) this;
        }

        public Criteria andRtTypeLessThanOrEqualTo(String value) {
            addCriterion("rt_type <=", value, "rtType");
            return (Criteria) this;
        }

        public Criteria andRtTypeLike(String value) {
            addCriterion("rt_type like", value, "rtType");
            return (Criteria) this;
        }

        public Criteria andRtTypeNotLike(String value) {
            addCriterion("rt_type not like", value, "rtType");
            return (Criteria) this;
        }

        public Criteria andRtTypeIn(List<String> values) {
            addCriterion("rt_type in", values, "rtType");
            return (Criteria) this;
        }

        public Criteria andRtTypeNotIn(List<String> values) {
            addCriterion("rt_type not in", values, "rtType");
            return (Criteria) this;
        }

        public Criteria andRtTypeBetween(String value1, String value2) {
            addCriterion("rt_type between", value1, value2, "rtType");
            return (Criteria) this;
        }

        public Criteria andRtTypeNotBetween(String value1, String value2) {
            addCriterion("rt_type not between", value1, value2, "rtType");
            return (Criteria) this;
        }

        public Criteria andRtStockIsNull() {
            addCriterion("rt_stock is null");
            return (Criteria) this;
        }

        public Criteria andRtStockIsNotNull() {
            addCriterion("rt_stock is not null");
            return (Criteria) this;
        }

        public Criteria andRtStockEqualTo(Integer value) {
            addCriterion("rt_stock =", value, "rtStock");
            return (Criteria) this;
        }

        public Criteria andRtStockNotEqualTo(Integer value) {
            addCriterion("rt_stock <>", value, "rtStock");
            return (Criteria) this;
        }

        public Criteria andRtStockGreaterThan(Integer value) {
            addCriterion("rt_stock >", value, "rtStock");
            return (Criteria) this;
        }

        public Criteria andRtStockGreaterThanOrEqualTo(Integer value) {
            addCriterion("rt_stock >=", value, "rtStock");
            return (Criteria) this;
        }

        public Criteria andRtStockLessThan(Integer value) {
            addCriterion("rt_stock <", value, "rtStock");
            return (Criteria) this;
        }

        public Criteria andRtStockLessThanOrEqualTo(Integer value) {
            addCriterion("rt_stock <=", value, "rtStock");
            return (Criteria) this;
        }

        public Criteria andRtStockIn(List<Integer> values) {
            addCriterion("rt_stock in", values, "rtStock");
            return (Criteria) this;
        }

        public Criteria andRtStockNotIn(List<Integer> values) {
            addCriterion("rt_stock not in", values, "rtStock");
            return (Criteria) this;
        }

        public Criteria andRtStockBetween(Integer value1, Integer value2) {
            addCriterion("rt_stock between", value1, value2, "rtStock");
            return (Criteria) this;
        }

        public Criteria andRtStockNotBetween(Integer value1, Integer value2) {
            addCriterion("rt_stock not between", value1, value2, "rtStock");
            return (Criteria) this;
        }

        public Criteria andRtPriceIsNull() {
            addCriterion("rt_price is null");
            return (Criteria) this;
        }

        public Criteria andRtPriceIsNotNull() {
            addCriterion("rt_price is not null");
            return (Criteria) this;
        }

        public Criteria andRtPriceEqualTo(Integer value) {
            addCriterion("rt_price =", value, "rtPrice");
            return (Criteria) this;
        }

        public Criteria andRtPriceNotEqualTo(Integer value) {
            addCriterion("rt_price <>", value, "rtPrice");
            return (Criteria) this;
        }

        public Criteria andRtPriceGreaterThan(Integer value) {
            addCriterion("rt_price >", value, "rtPrice");
            return (Criteria) this;
        }

        public Criteria andRtPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("rt_price >=", value, "rtPrice");
            return (Criteria) this;
        }

        public Criteria andRtPriceLessThan(Integer value) {
            addCriterion("rt_price <", value, "rtPrice");
            return (Criteria) this;
        }

        public Criteria andRtPriceLessThanOrEqualTo(Integer value) {
            addCriterion("rt_price <=", value, "rtPrice");
            return (Criteria) this;
        }

        public Criteria andRtPriceIn(List<Integer> values) {
            addCriterion("rt_price in", values, "rtPrice");
            return (Criteria) this;
        }

        public Criteria andRtPriceNotIn(List<Integer> values) {
            addCriterion("rt_price not in", values, "rtPrice");
            return (Criteria) this;
        }

        public Criteria andRtPriceBetween(Integer value1, Integer value2) {
            addCriterion("rt_price between", value1, value2, "rtPrice");
            return (Criteria) this;
        }

        public Criteria andRtPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("rt_price not between", value1, value2, "rtPrice");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table h_roomtype
     *
     * @mbg.generated do_not_delete_during_merge Tue Jul 30 09:29:41 CST 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table h_roomtype
     *
     * @mbg.generated Tue Jul 30 09:29:41 CST 2019
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}