import React, {useEffect} from "react";
import {Layout, message} from "antd";
import Spinner from "../components/spinner";
import {useSelector, shallowEqual} from "react-redux";
import {selectSpinnerState} from "../selector/spinner";
import {selectMessageData} from "../selector/message";
import HeaderSection from "../components/header";

const LandingPage = ({children}) => {
  const {Header, Content} = Layout;
  const spinnerLoad = useSelector(selectSpinnerState, shallowEqual);
  const messageData = useSelector(selectMessageData, shallowEqual);

  useEffect(() => {
    if (messageData) {
      const {type, detail} = messageData;
      switch (type) {
        case "success": {
          message.success(detail, 3);
          break;
        }
        case "info": {
          message.info(detail, 3);
          break;
        }
        case "error": {
          message.error(detail, 3);
          break;
        }
        case "warning": {
          message.warning(detail, 3);
          break;
        }
        default: {
          message.info(detail, 3);
        }
      }
    }
  }, [messageData]);

  return (
    <Spinner load={spinnerLoad}>
      <Layout>
        <Header
          style={{
            position: "fixed",
            zIndex: 1,
            width: "100%",
            backgroundColor: "white",
            padding: "0 10px",
          }}
        >
          <HeaderSection />
        </Header>
        <Content style={{marginTop: "64px", minHeight: "calc(100vh - 64px)"}}>
          {children}
        </Content>
      </Layout>
    </Spinner>
  );
};

export default LandingPage;
