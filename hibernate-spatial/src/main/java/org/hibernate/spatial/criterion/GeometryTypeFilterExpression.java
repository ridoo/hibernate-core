package org.hibernate.spatial.criterion;

import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.spatial.GeometryType;
import org.hibernate.spatial.SpatialDialect;
import org.hibernate.spatial.SpatialFunction;
import org.hibernate.type.StandardBasicTypes;

public class GeometryTypeFilterExpression implements Criterion {
    
    private final String propertyName;
    private final GeometryType.Type geometryType;

    public GeometryTypeFilterExpression(String propertyName, GeometryType.Type geometryType) {
        this.propertyName = propertyName;
        this.geometryType = geometryType;
    }
    
    @Override
    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
        return getSpatialDialect(criteriaQuery).getGeometryTypeSQL(getColumn(criteria, criteriaQuery));
    }

    @Override
    public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
        return new TypedValue[]{
                new TypedValue(StandardBasicTypes.STRING, getSpatialDialect(criteriaQuery).getGeometryQueryType(geometryType), EntityMode.POJO)};
    }

    private String getColumn(Criteria criteria, CriteriaQuery criteriaQuery) {
        return ExpressionUtil.findColumn(propertyName, criteria, criteriaQuery);
    }
    
    private SpatialDialect getSpatialDialect(CriteriaQuery criteriaQuery){
        return ExpressionUtil.getSpatialDialect(criteriaQuery, SpatialFunction.geometrytype);
    }
}
