#!/bin/bash -e

die() {
  local msg="${1?}"
  echo -e "FATAL: ${msg}"
  exit 1
}

log_info() {
  local msg="${1?}"
  echo "INFO: `date`: ${msg}"
}

validate() {
  log_info "Validating input variables.."
  [[ -n "${GPG_SECRET_KEY}" ]] || die "Please provide gpg key"
  [[ -n "${GPG_PASSPHRASE}" ]] || die "Please provide gpg passphrase"

  log_info "Validating CLI tools.."
  [[ -x "$(command -v gpg)" ]] || die "gpg command not installed"
}


main() {
  validate

  echo ${GPG_SECRET_KEY} | base64 --decode | gpg --batch --import

  mvn deploy --settings sonatype-settings.xml -DskipTests=true -Psign-artifacts -Dgpg.passphrase=${GPG_PASSPHRASE} -B
}

main
