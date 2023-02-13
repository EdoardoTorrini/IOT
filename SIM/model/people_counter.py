from base import Base


class PeopleCounter(Base):

    def __init__(self, sID="", nPeopleIn=0, nPeopleOut=0):

        super(PeopleCounter, self).__init__(sID)
        self.nPeopleIn = nPeopleIn
        self.nPeopleOut = nPeopleOut

    def __repr__(self):

        return f"[ PEOPLE COUNTER ] -> " \
               f"[ ID ]: {self.sID} " \
               f"[ PEOPLE IN ]: {self.nPeopleIn}" \
               f"[ PEOPLE OUT ]: {self.nPeopleOut}" \
               f"[ TIME ]: {self.data}"
