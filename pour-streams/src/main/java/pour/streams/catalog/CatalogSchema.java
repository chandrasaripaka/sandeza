package pour.streams.catalog;

import java.util.Vector;

import pour.streams.schema.SchemaField;

/**
 * CatalogSchema – represents a stream’s schema, and consists of a set of typed fields, internally maintained
 as a vector. These fields are instances of the SchemaField class, and track the name, type, size
 and offset of the field within the schema. The size and offset fields are used to access the value of a
 field within a tuple during expression evaluation when an operator runs
 */
public class CatalogSchema {

    private Vector<SchemaField> schemaFieldVector;

}
