from sqlalchemy.orm import sessionmaker


class Session:

    def __init__(self, engine):

        session = sessionmaker(bind=engine, autoflush=True, expire_on_commit=True)
        self.session = session()
