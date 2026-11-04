FROM python:3.12-slim
WORKDIR /app
RUN pip install flask requests
COPY app.py .
EXPOSE 5000
CMD ["flask", "run", "--host", "0.0.0.0"]