from sqlalchemy.exc import IntegrityError


class CTable:

    __session__ = None

    def insert(self):
        try:
            if self.__session__ is not None:
                self.__session__.add(self)
                self.__session__.commit()
            else:
                raise Exception("session is None")

        except IntegrityError:
            raise KeyError(f"[ sqlalchemy.exc.IntegrityError ]: UNIQUE constraint failed")

        except Exception as err:
            print(f"ERR -> [ MESSAGE ]: {err}, [ TYPE ]: {type(err)}")

    def update(self):
        try:
            if self.__session__ is not None:
                self.__session__.commit()
            else:
                raise Exception("session is None")

        except Exception as err:
            print(f"ERR -> [ MESSAGE ]: {err}, [ TYPE ]: {type(err)}")
