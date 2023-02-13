from os import path, getcwd
from sqlalchemy import create_engine


class Engine:

    def __init__(self, db="ids.db"):

        BASE_DIR = getcwd()
        DB_DIR = path.join(BASE_DIR, db)

        self.engine = create_engine(
            "sqlite:///{}".format(DB_DIR),
            echo=True,
            future=True,
            connect_args={'check_same_thread': False}
        )
