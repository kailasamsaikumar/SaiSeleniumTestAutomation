FROM markhobson/maven-chrome:jdk-11

# xvfb, ffmpeg, tmux
RUN apt-get update && \
    apt-get install -y xvfb && \
    apt-get install -y ffmpeg && \
    apt-get install -y tmux
