from model import Base


class PeopleCounter(Base):

    def __init__(self, code="", people_in=0):

        super(PeopleCounter, self).__init__(code)
        self.peopleIn = people_in

    def __repr__(self):

        return f"[ PEOPLE COUNTER ] -> " \
               f"[ ID ]: {self.id} " \
               f"[ PEOPLE IN ]: {self.peopleIn}" \
               f"[ TIME ]: {self.data}"
