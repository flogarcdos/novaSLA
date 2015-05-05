package co.dedalus.novaSLA.config;

import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsAnnotationConfiguration;
import org.hibernate.MappingException;
import org.hibernate.mapping.JoinedSubclass;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.RootClass;

import java.util.Iterator;

public class TablePerSubclassConfiguration extends GrailsAnnotationConfiguration {

    private static final long serialVersionUID = 1;

    private boolean alreadyProcessed = false;

    @Override
    protected void secondPassCompile() throws MappingException {
        super.secondPassCompile();

        if (alreadyProcessed) {
            return;
        }

        for (PersistentClass persistentClass : classes.values()) {
            if (persistentClass instanceof RootClass) {
                RootClass rootClass = (RootClass) persistentClass;

                if (rootClass.hasSubclasses()) {
                    Iterator subclasses = rootClass.getSubclassIterator();

                    while (subclasses.hasNext()) {

                        Object subclass = subclasses.next();

                        // This test ensures that foreign keys will only be created for subclasses that are
                        // mapped using "table per subclass"
                        if (subclass instanceof JoinedSubclass) {
                            JoinedSubclass joinedSubclass = (JoinedSubclass) subclass;
                            joinedSubclass.createForeignKey();
                        }
                    }
                }
            }
        }

        alreadyProcessed = true;
    }
}