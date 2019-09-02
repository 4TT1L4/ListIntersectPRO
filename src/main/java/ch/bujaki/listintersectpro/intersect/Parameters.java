package ch.bujaki.listintersectpro.intersect;

import ch.bujaki.listintersectpro.controller.UserInterfaceAccessor;

/**
 * Configurations parameters for the execution of the intersection computation algorithm.
 *
 * The configuration parameters represent the state of the settings available on the user interface, when the process
 * was triggered by the user.
 *
 * This class is only a very simple data container class, that bundles together the data, that described the
 * configuration of the .
 */
class Parameters {
    final int eventId;
    final int sizeOfListA;
    final int sizeOfListB;
    final UserInterfaceAccessor.InputList listToPutInHashMap;

    /**
     * Constructor.
     */
    Parameters(int eventId, int sizeOfListA, int sizeOfListB, UserInterfaceAccessor.InputList listToPutInHashMap) {
        this.eventId = eventId;
        this.sizeOfListA = sizeOfListA;
        this.sizeOfListB = sizeOfListB;
        this.listToPutInHashMap = listToPutInHashMap;
    }

    /**
     * @InheritDoc
     */
    @Override
    public String toString() {
        return "Parameters{" +
                "event=" + eventId +
                ", sizeOfListA=" + sizeOfListA +
                ", sizeOfListB=" + sizeOfListB +
                ", listToPutInHashMap=" + listToPutInHashMap +
                '}';
    }
}