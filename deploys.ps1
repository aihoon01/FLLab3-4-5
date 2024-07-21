#Variables
$projectDir = "C:\Users\StephenAihoon\Desktop\FLLabs\JavaUpskillLabs-lab-3-4-5\blogapp_springboot"
$warFilename = "blogapp_springboot-0.0.1-SNAPSHOT.war"
$warFilePath = "$projectDir\build\libs\$warFileName"


$tomcatDir = "C:\Users\StephenAihoon\Downloads\apache-tomcat-10.1.26"
$tomcatWebappsDir = "$tomcatDir\webapps"
$tomcatShutdownScript = "$tomcatDir\bin\shutdown.bat"
$tomcatStartupScript = "$tomcatDir\bin\startup.bat"
$unpackedDirPath = "$tomcatWebappsDir\${warFileName.Substring(0, $warFileName.Length - 4)}"


$buildClean = "./gradlew clean"
$buildCommand = "./gradlew build"

$CATALINA_HOME = "C:\Users\StephenAihoon\Downloads\apache-tomcat-10.1.26"


#Change to Project Directory
cd $projectDir

#Build the Project
Write-Host "Building the project..."
Invoke-Expression $buildClean
Invoke-Expression $buildCommand

# Check if the build was successful
if ($LASTEXITCODE -ne 0) {
	Write-Host "Build failed. Exiting..."
	exit $LASTEXITCODE
}

# Stop TomCat
Write-Host "Stopping Tomcat..."
Write-Host "Stopping Tomcat..."
Write-Host "Stopping Tomcat..."
Write-Host "..."
Invoke-Expression $tomcatShutdownScript

# Wait for Tomcat to stop
Start-Sleep -Seconds 10
Write-Host "Tomcat Stopped..."

# Copy new WAR file to Tomcat webapps directory
Write-Host "Deploying new WAR file..."
Copy-Item $warFilePath $tomcatWebappsDir
Write-Host "New WAR file Deployed" -ForegroundColor Green

# Start Tomcat
Write-Host "Starting Tomcat..." -ForegroundColor Yellow
Invoke-Expression $tomcatStartupScript

Write-Host "Deployment Successful!!!." -ForegroundColor Green
