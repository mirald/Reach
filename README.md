Reach - a cityteering application to make your runs more fun!

To make the code work, you need to have a Mapquest API Key, and declare it in your global gradle.properties file as such:

1. Locate the .gradle directory

On Mac:
Users/<your-name>/.gradle

On Linux:
(Untested) /home/<your-name>/.gradle

On Windows:
(Untested) C:\Users\<your-name>\.gradle

2. Open gradle.properties using vim or any ther text editor. If there is no gradle.properties, create it

3. Add api-key in gradle.properties:

MAPQUEST_API_KEY="<your-api-key>"

4. Save and restart Android Studios, maybe you have to sync gradle again or rebuild the project

