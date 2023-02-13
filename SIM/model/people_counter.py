from model import Base


class PeopleCounter(Base):

    def __init__(self, code="", people_in=0, people_out=0):

        super(PeopleCounter, self).__init__(code)
        self.nPeopleIn = people_in
        self.nPeopleOut = people_out

    def __repr__(self):

        return f"[ PEOPLE COUNTER ] -> " \
               f"[ ID ]: {self.sID} " \
               f"[ PEOPLE IN ]: {self.nPeopleIn}" \
               f"[ PEOPLE OUT ]: {self.nPeopleOut}" \
               f"[ TIME ]: {self.data}"
