jenkins:
	docker-compose -f ./scripts/jenkins.yml up -d
	docker-compose -f ./scripts/jenkins.yml logs -f

jenkins_down:
	docker-compose -f ./scripts/jenkins.yml down

gitlab:
	docker-compose -f ./scripts/gitlab.yml up -d
	docker-compose -f ./scripts/gitlab.yml logs -f

gitlab_down:
	docker-compose -f ./scripts/gitlab.yml down

sonar:
	docker-compose -f ./scripts/sonarqube.yml up -d
	docker-compose -f ./scripts/sonarqube.yml logs -f

sonar_down:
	docker-compose -f ./scripts/sonarqube.yml down

nexus:
	docker-compose -f ./scripts/nexus.yml up -d
	docker-compose -f ./scripts/nexus.yml logs -f

nexus_down:
	docker-compose -f ./scripts/nexus.yml down

gitlab:
	docker-compose -f ./scripts/gitlab.yml up -d
	docker-compose -f ./scripts/gitlab.yml logs -f

gitlab_down:
	docker-compose -f ./scripts/gitlab.yml down
